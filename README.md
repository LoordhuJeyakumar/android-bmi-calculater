# BMI Calculator Android App

This is a simple Android application that calculates a user's Body Mass Index (BMI) based on their weight and height. The app provides the calculated BMI value, the corresponding BMI category (e.g., Underweight, Normal weight, Overweight, Obese), and some general health advice.

## Features

*   **BMI Calculation:** Calculates BMI using the standard formula (weight in kg / (height in meters)^2).
*   **BMI Categories:** Displays the user's BMI category based on the calculated value.
*   **Health Advice:** Provides general health advice based on the user's BMI category.
*   **Clear Inputs:** Allows the user to easily clear the input fields.
*   **Share Results:** Enables the user to share their BMI results with others.
*   **User-Friendly Interface:** Features a clean and intuitive user interface with Material Design components.
*   **Real-time Validation:** Provides real-time feedback on the input fields to ensure valid data is entered.

## Architecture

The application follows the Model-View-ViewModel (MVVM) architectural pattern, which separates the application's logic into three interconnected components:

*   **Model (`BMIModel.java`):** Represents the data and business logic of the application. In this case, it holds the user's weight, height, calculated BMI, and BMI category.
*   **View (`MainActivity.java`, `activity_main.xml`):** Represents the UI of the application. It observes the ViewModel for changes in the data and updates the UI accordingly.
*   **ViewModel (`BMIViewModel.java`):** Acts as a bridge between the Model and the View. It holds the application's UI-related data and exposes it to the View. It also handles user interactions and updates the Model.

## Screenshots

*(Screenshots of the application would go here)*

## Getting Started

To build and run the project, you will need:

*   Android Studio
*   An Android device or emulator

1.  Clone the repository: `git clone https://github.com/LoordhuJeyakumar/android-bmi-calculater`
2.  Open the project in Android Studio.
3.  Build the project and run it on an Android device or emulator.

## Dependencies

The application uses the following libraries:

*   **AndroidX AppCompat:** Provides backward-compatible versions of Android framework APIs.
*   **AndroidX ConstraintLayout:** A flexible layout manager for creating complex layouts.
*   **AndroidX Lifecycle (ViewModel and LiveData):** Used to implement the MVVM architecture.
*   **Google Material Components:** Provides a set of Material Design components for building beautiful and functional user interfaces.
*   **JUnit:** A testing framework for Java.
*   **AndroidX Test:** A set of testing libraries for Android.
