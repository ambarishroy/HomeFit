HomeFit Technical Report

Introduction
HomeFit is an Android application designed to help users maintain a healthy lifestyle by performing exercises at home. The app targets users who prefer home-based fitness routines without requiring gym equipment. The app is built using the MVVM architectural pattern and leverages Google sign in and Firebase for user authentication and data storage. The users can also register themselves and in case they forget their password, they can even created a new password seamlessly. The project is hosted on GitHub and follows modern development practices.

Functionality
1. User Registration and Authentication via Google Sign-In and Firebase Authentication.
2. Personal Profile Setup includes height, weight, date of birth and weekly calorie target.
3. Exercise tracking by calorie burn progress.
4. BMI checking.
5. Persistent Bottom Navigation for seamless navigation between screens.

![UML diagram](https://github.com/user-attachments/assets/d3b6f49f-5107-4e25-8898-7ed118d6476b)


APIs used
1. Firebase Authentication: This enables secure sign-in and sign-up processes, including Google Sign-In and One Tap Sign-In as fallback. Password can also be reset.
2. Firebase: Firebase stores user profile data and calorie tracking information.
3. Google sign-in: This API integrates Google login with Firebase for smooth authentication.
4. Hilt: Hilt is used for dependency injection to manage the lifecycle of the components.

Architecture and UX/DX

The app follows the Model-View-ViewModel (MVVM) architecture, enhancing modularity and testability. ViewModels interact with the repository layer to fetch and update data and feed it to the UI. In terms of traditional, jetpack compose or hybrid, HomeFit employs a hybrid approach, utilizing both traditional XML-based layouts and Jetpack Compose for specific interactive UI components. For stability and ease of navigation, persistent bottom navigation bar is implemented by traditional XML. The profile setup form is also based on XML layouts.The exercise cards and dynamic components utilize Jetpack Compose for a more fluid and interactive experience.

Personal Statement

Building HomeFit has been an enriching experience that allowed me to integrate Android development best practices with modern libraries such as Jetpack Compose and Hilt. Overcoming challenges related to dependency management and UI consistency provided crucial and valuable lessons. Through this project, I have gained hands-on experience with Firebase Authentication and cloud data management using Firestore and Google API.
