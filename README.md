# Birthday Reminder

This is a web-based Birthday Reminder application that allows users to save birthdays and receive reminders three days before and on the day of each birthday. The app includes categories for organizing birthdays into "Family," "Friends," and "Work" groups.

## Features

- **Add, Edit, and Delete Birthdays**: Users can easily manage birthdays by adding, editing, or deleting entries.
- **Category Filters**: Filter birthdays by category - "All," "Family," "Friends," and "Work."
- **Reminders**: A popup notification reminds users of upcoming birthdays (within 3 days) and on the day of the birthday.
- **Persistent Storage**: All data is saved in `localStorage`, so birthdays are saved across sessions.

## How to Use

1. **Add a Birthday**:
   - Enter the name, date, and select a category.
   - Click the "Save Birthday" button to add it to the list.

2. **Edit or Delete a Birthday**:
   - Click "Edit" to modify an entry or "Delete" to remove it.

3. **View Reminders**:
   - A popup notification will show up three days before and on the day of each birthday.

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/birthday-reminder.git

2. Navigate into the project directory:
cd birthday-reminder

3. Open the index.html file in a web browser to use the application.

Technologies Used
- HTML
- CSS
- JavaScript (localStorage for persistent data)

## What is what?

1. **HTML without database**:
   - Index.html
2. **HTML with database**:
   - Index2.html
3. **Android**:
   - activity_main.xml
   - MainActivity.kt
  
## Steps to Generate APK
- Install Android Studio on your machine.
- Create a new project and copy the above files into the respective locations (MainActivity.kt in src/main/java and activity_main.xml in res/layout).
- Build the project by selecting Build > Build Bundle(s)/APK(s) > Build APK(s).
- The APK file will be generated in the app/build/outputs/apk directory.

Index
<img width="810" alt="image" src="https://github.com/user-attachments/assets/dc6dc077-1f12-477f-89b8-3016ddc578a2">


Index2.0
<img width="823" alt="image" src="https://github.com/user-attachments/assets/0bfd5ab1-84b2-4135-a09b-1fb0d13255a1">


