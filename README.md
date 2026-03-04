# Language Learner Android App

An Android application based on [ZejdK's Language Learner]((https://github.com/ZejdK/LanguageLearner)) website, designed to help users learn new languages through an interactive and user-friendly interface.

## 🚀 Features

*   **Authentication:** Secure user login and registration screens.
*   **Home Dashboard:** A central hub to track progress and access learning materials.
*   **User Profile:** Manage user settings and personal information.
*   **Progress Statistics:** (In Development) Visualize your learning journey and milestones.
*   **Modern Navigation:** Seamless transitions between screens using a bottom navigation bar.

## 🛠 Tech Stack

*   **Language:** [Kotlin](https://kotlinlang.org/)
*   **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose) - For building modern, declarative UIs.
*   **Dependency Injection:** [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - To manage project dependencies efficiently.
*   **Networking:** [Retrofit](https://square.github.io/retrofit/) - For making API calls to a backend server.
*   **Navigation:** [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) - For handling app navigation.
*   **Local Storage:** [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - For storing user preferences and lightweight data.
*   **Design:** [Material 3](https://m3.material.io/) - Google's latest design system.

## 📂 Project Structure

The project follows a modular structure based on clean architecture principles:

*   `ui/`: Contains all Compose-based UI components, organized by feature (auth, home, profile).
*   `data/`: Handles data operations, including models, repositories, and remote API definitions.

## 🏁 Getting Started

### Prerequisites

*   Android Studio Ladybug (or newer)
*   JDK 17
*   Android SDK 36 (Target)

### Installation

1.  Clone the repository:
    ```bash
    git clone https://github.com/mmulalic/LanguageLearnerAndroidApp.git
    ```
2.  Open the project in Android Studio.
3.  Sync the project with Gradle files.
4.  Run the application on an emulator or a physical device.
