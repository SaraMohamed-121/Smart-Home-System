# Smart Home System 📱 
---

## 📋 Project Overview

This project aims to develop a **remote smart control solution** that integrates **embedded electronic circuits** with a **mobile application**. The system provides **real-time monitoring**, **remote control**, and **seamless connectivity** between hardware components and cloud-based services using **Firebase** and **SQLite**.

---

## 🛠️ Hardware Requirements

### Common Components:
- Breadboard
- Jump wires
- LEDs
- ESP8266NodeMCU / Arduino Uno + ESP8266 WiFi Module

### Application-Specific Components:
- LM35 Temperature Sensor 🌡️
- Ultrasonic Sensor 📏
- DC Motor 🔄
- LCD 🖥️
- Keypad 🔢

---

## 📱 Mobile Application Features

### 1️⃣ **User Authentication**
- **Registration Activity**: Fields: name, username, password, confirm password, profile picture, email, birthdate (stored in Firebase and cached in SQLite).
- **Login Activity**: Support for both normal login and Firebase authentication.
  - **Remember Me** with a checkbox.
  - **Forgot Password** feature.

### 2️⃣ **Main Home Activity**
- Displays a list of available smart actions using **ListView** or **RecyclerView**.
- Each item includes an image and title, navigating to its corresponding action activity when clicked.

### 3️⃣ **Search Functionality**
- Filter actions by title via the **App Bar Search**.

### 4️⃣ **Options Menu**
- **Activity Log**: Tracks all user actions with timestamps (stored in Firebase and SQLite).
- **Profile**: Displays user profile with logout functionality.
- **Logout**: Logs out and returns to the login form.

---

## 🏡 Smart Home Features

1. **Temperature Monitoring** 🌡️  
   Measure and display room temperature via LM35, synced to Firebase.

2. **Password-Based Door Lock** 🔐  
   Set and store a home password on Firebase; unlock via keypad entry.

3. **Light Control** 💡  
   Toggle LEDs remotely using a boolean signal from the mobile app.

4. **Fan Control**   
   Remotely control fan motors through the mobile app.

5. **Intrusion Detection** 🚨  
   Detects unauthorized entry using an Ultrasonic Sensor and raises an alert if triggered.

6. **Message Display (Bonus)** ✉️  
   Display custom messages from the mobile app on an LCD.
