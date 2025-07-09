# CashiApp 
A Simple app to demostrate payment flow with realtime transactions history using up mock server.

<div align="center">
  <sub>Built with ❤︎ by
  <a>Muhammad Usama Yasin</a>
</div>
<br/>

## Features
* Home Screen showing list of transactions.
* User can add payment using email , amount and valid currency.
* Form validations implemented.
* New Transaction will be shown on the top of the list and in realtime.
* Using local json mock server to call API and then moving up to publish data on firestore.

## Architecture
* Built with Modern Development practices and utilities shared module for core business.
* Utilized Usecase, Repository pattern for data.
* Includes valid Unit tests for Repository and Usecases.

## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
- [Dependency Injection](https://developer.android.com/training/dependency-injection)
  - [Koin](https://dagger.dev/koin) - Easier way to incorporate Dagger DI into mobile apps.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - For writing Gradle build scripts using Kotlin.


## Improvements:
 - Add Abstraction for use cases , and add base classes.
- Implement DI in shared module since KMP didn’t support natively so I added it in Android Specific.
 - Add more Unit/UI Tests

# Setup
- Clone the repository
- First need to run the server
- cd mock-server
- run local json server "npx json-server --watch db.json --port 3000"
- then launch the app


# Screenshots
![image](https://github.com/user-attachments/assets/eecd638b-b302-4adc-9b79-d62fb16b9c3c)

https://github.com/user-attachments/assets/6eb584f4-fce3-4873-8bd1-9b0543ee5947



## 👨 Developed By
**Muhammad Usama Yasin**

[![Linkedin](https://img.shields.io/badge/-linkedin-grey?logo=linkedin)](www.linkedin.com/in/-usama-yasin)
