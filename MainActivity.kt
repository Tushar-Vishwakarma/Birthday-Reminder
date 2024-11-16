package com.example.birthdayreminder

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var birthdayList: MutableList<Birthday>
    private lateinit var adapter: BirthdayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize birthday list
        birthdayList = loadBirthdays()

        // Set up RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        adapter = BirthdayAdapter(birthdayList, this::editBirthday, this::deleteBirthday)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Add birthday button
        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener { addBirthday() }

        // Notification channel
        createNotificationChannel()

        // Check for reminders
        checkReminders()
    }

    private fun addBirthday() {
        val nameInput = findViewById<EditText>(R.id.nameInput)
        val dateInput = findViewById<DatePicker>(R.id.datePicker)

        val name = nameInput.text.toString()
        val day = dateInput.dayOfMonth
        val month = dateInput.month
        val year = dateInput.year
        val birthdayDate = Calendar.getInstance()
        birthdayDate.set(year, month, day)

        if (name.isNotEmpty()) {
            val birthday = Birthday(name, birthdayDate.time)
            birthdayList.add(birthday)
            saveBirthdays()
            adapter.notifyDataSetChanged()
            nameInput.text.clear()
            Toast.makeText(this, "Birthday added!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Please enter a name!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun editBirthday(position: Int) {
        val birthday = birthdayList[position]
        val nameInput = findViewById<EditText>(R.id.nameInput)
        val datePicker = findViewById<DatePicker>(R.id.datePicker)

        nameInput.setText(birthday.name)
        val calendar = Calendar.getInstance()
        calendar.time = birthday.date
        datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        birthdayList.removeAt(position)
        adapter.notifyDataSetChanged()
    }

    private fun deleteBirthday(position: Int) {
        birthdayList.removeAt(position)
        saveBirthdays()
        adapter.notifyDataSetChanged()
        Toast.makeText(this, "Birthday deleted!", Toast.LENGTH_SHORT).show()
    }

    private fun saveBirthdays() {
        val sharedPreferences = getSharedPreferences("birthdays", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val json = birthdayList.joinToString(separator = ";") { "${it.name},${it.date.time}" }
        editor.putString("birthdayList", json)
        editor.apply()
    }

    private fun loadBirthdays(): MutableList<Birthday> {
        val sharedPreferences = getSharedPreferences("birthdays", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("birthdayList", null)
        return json?.split(";")?.map {
            val parts = it.split(",")
            Birthday(parts[0], Date(parts[1].toLong()))
        }?.toMutableList() ?: mutableListOf()
    }

    private fun checkReminders() {
        val today = Calendar.getInstance()
        birthdayList.forEach { birthday ->
            val calendar = Calendar.getInstance()
            calendar.time = birthday.date
            if (calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                calendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
                showNotification("${birthday.name}'s birthday is today!")
            }
        }
    }

    private fun showNotification(message: String) {
        val notification = NotificationCompat.Builder(this, "birthdayChannel")
            .setContentTitle("Birthday Reminder")
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "birthdayChannel",
                "Birthday Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}

data class Birthday(val name: String, val date: Date)
