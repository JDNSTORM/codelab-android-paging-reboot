# Advanced Paging Codelab - Compose Multiplatform Reboot

This project is a Compose Multiplatform adaptation of the original Android Paging Advanced Codelab. It demonstrates how to implement pagination using the Paging library in a Kotlin Multiplatform project targeting Android, iOS, and Desktop.

## Original Codelab

The original codelab, "[Android Paging Advanced Codelab](https://developer.android.com/codelabs/android-paging)", guides you through adding the Paging library to an Android app to load and display data in a `RecyclerView`.

## Compose Multiplatform Reboot

This project takes the concepts from the original codelab and applies them to a Compose Multiplatform environment. Instead of `RecyclerView`, it utilizes Compose UI to display the paginated data. The core Paging logic remains similar, showcasing its versatility across different platforms.

## Features

*   **Paging Library Integration:** Demonstrates how to set up and use the Paging library for efficient data loading.
*   **Compose Multiplatform UI:** Implements the UI using Jetpack Compose for Android and Compose Multiplatform for iOS and Desktop.
*   **Jetpack Room for local data persistence:** Utilizes Room for caching data locally.
*   **Platform-Specific Implementations:** Shows how to handle platform-specific dependencies and UI components where necessary.
*   **Koin for Dependency Injection:** Utilizes Koin for managing dependencies across the different modules and platforms.

## Project Structure

The project is organized into several modules:

*   `composeApp`: Contains the shared UI code written in Compose Multiplatform, as well as platform-specific entry points for Android, iOS, and Desktop.
*   `core:data:local`: Handles local data storage using Jetpack Room.
*   `core:data:network`: Manages network requests for fetching data using Ktor.
*   `core:models`: Defines the data models used throughout the application.
*   `core:repositories`: Implements the repository pattern to abstract data sources.
*   `core:designSystem`: Contains shared Composable UI components for a consistent look and feel.
*   `pagingCompose`: Provides an implementation of paging UIs based on the `androidx.paging.compose` library, adapted for multiplatform use.

## How to Run

### Android

1.  Open the project in Android Studio.
2.  Select the `composeApp` run configuration.
3.  Choose an Android emulator or connected device.
4.  Click the "Run" button.

### Desktop

1.  Open the project in IntelliJ IDEA (or Android Studio).
2.  Select the `run` Gradle task (usually found under `composeApp > Tasks > compose desktop`).
3.  Run the task.

### iOS

1.  Open the `iosApp/iosApp.xcworkspace` file in Xcode.
2.  Select a simulator or connected iOS device.
3.  Click the "Run" button.
    *Note: You might need to set up your development team in Xcode.*

## Known Bugs

*   [Paging-compose unusual jump behavior when displaying paged data + remote mediator](https://issuetracker.google.com/235319241)

## Acknowledgements

*   This project is derived from the official [Android Paging Advanced Codelab](https://developer.android.com/codelabs/android-paging).
*   Built with [Kotlin Multiplatform](https://kotlinlang.org/lp/multiplatform/) and [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/).
