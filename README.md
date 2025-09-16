# 📚 School Management System (Android)

An Android app built in **Java** using **Android Studio** that allows administrators, instructors, and students to manage academic records and tasks efficiently.

---

## ✨ Features

### 👨‍💼 Admin
- Manage **Students** (CRUD operations: Create, Read, Update, Delete)
- Manage **Instructors** (CRUD operations)
- Manage **Modules** (CRUD operations: add modules, update details, delete modules)

### 👨‍🏫 Instructor
- Create and assign **tasks** to students
- Link tasks to a specific **module**
- Track task status

### 👨‍🎓 Student
- View tasks assigned to them
- Delete tasks
- Toggle task status (completed / not completed)

---

## 🛠️ Tech Stack
- **Java** (Android development)
- **Android Studio**
- SQLite (for local database)

---

## 🚀 Getting Started

Follow these steps to set up the project locally:

### 1. Clone the Repository
```bash
git clone  https://github.com/BKani23/School-Management-System-.git
```

### 2. Open in Android Studio

Launch Android Studio

Click File > Open

Select the cloned project folder

Allow Android Studio to import Gradle project files

### 3. Configure SDK

Go to File > Project Structure > SDK Location

Ensure you have the Android SDK installed

Recommended: Android API Level 30+

### 4. Build the Project

Android Studio will sync all required Gradle dependencies automatically

If prompted, install missing SDK packages

Wait until you see Build Successful

### 5. Run the App

Connect an Android device with USB Debugging enabled

Or set up an Android Emulator in Android Studio

Click the green Run ▶️ button

Select your device/emulator → the app will install and launch 🚀

### 6. Database Setup

The app uses SQLite, so no external setup is required

The database is created automatically on first launch
