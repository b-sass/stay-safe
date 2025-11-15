# Stay Safe - Android Application

**Stay Safe** is a modern Android application designed to enhance personal security through location tracking, contact management, and safety zone definitions. Built entirely with **Jetpack Compose** and leveraging modern Android development practices, the app provides a seamless and intuitive user experience.

---

## ✨ Features

*   **🗺️ Interactive Map View:** The central hub of the application, showing the user's current location.
*   **👥 Contact Management:** Users can manage a list of trusted contacts within the app.
*   **📍 Safe Places:** Define and manage a list of "safe" locations (e.g., home, work, school).
*   **⚙️ Settings & Stats:** A tabbed view within the profile to manage app settings and view personal statistics.
*   **🚀 Modern UI:** A clean, single-activity architecture built with Jetpack Compose for a responsive and modern user interface.
*   **🧭 Type-Safe Navigation:** Utilizes Kotlinx Serialization and Jetpack Navigation's type-safe routing for robust and error-free screen transitions.

---

## 📸 Screenshots

|           Map View            |    Profile & Settings    |
|:-----------------------------:|:------------------------:|
| *[Map](/assets/MapView(user).jpg)* | *[Profile](/assets/Settings.jpg)* |

|      Contacts List       |       Places       |
|:------------------------:|:------------------:|
| *[Contacts](/assets/Contacts.jpg)* | *[Places](/assets/Places)* |

---

## 🛠️ Tech Stack & Architecture

This project is built using a modern Android tech stack, emphasizing best practices and maintainability.

*   **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) for the entire UI layer.
*   **Material 3:** For modern Material Design components.
*   **Navigation:** [Compose Navigation](https://developer.android.com/jetpack/compose/navigation) for navigating between composable screens.
*   **Architecture:** Follows the recommended MVVM (Model-View-ViewModel) pattern.
*   **Asynchronous Programming:** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-guide.html) for managing background threads and asynchronous operations.
*   **Networking:** [Ktor Client](https://ktor.io/docs/client-overview.html) for making API calls to a backend service.
*   **Data Serialization:** [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) for parsing JSON data.
*   **Mapping:** [Google Maps Compose Library](https://developers.google.com/maps/documentation/android-sdk/maps-compose) for integrating Google Maps.

---

## 🚀 Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

*   Android Studio Iguana | 2023.2.1 or newer.
*   Gradle 8.4 or newer.
*   A Google Maps API Key.

### Installation

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/your-username/stay-safe.git
    ```
2.  **Add your Google Maps API Key:**
    *   Obtain a free API key from the [Google Cloud Console](https://console.cloud.google.com/google/maps-apis/).
    *   Open your `secrets.properties` file (create it if it doesn't exist in the project root).
    *   Add your API key:
        ```properties
        MAPS_API_KEY="YOUR_API_KEY_HERE"
        ```
3.  **Build and run the project** in Android Studio.

---
