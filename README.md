# Koddit - A Kotlin Multiplatform Reddit Reader

I love Kotlin and Reddit, so I decided to try to make a simple Reddit reader that will that attempts at sharing as much code as possible between the Android and iOS platforms. 

## Multiplatform Libraries Used 

* [SQLDelight](https://github.com/cashapp/sqldelight) - sqlite abstraction over multiple platforms to persist data 
* [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) - serialize JSON for all of the platforms
* [Ktor](https://ktor.io/) - used for all networking on the clients
* [Kodein](https://kodein.org/) - dependency injection framework supported on every platform

## Tutorials Utilized

* [Targeting Android and iOS with Kotlin Multiplatform](https://play.kotlinlang.org/hands-on/Targeting%20iOS%20and%20Android%20with%20Kotlin%20Multiplatform/01_Introduction) - hands on tutorial from Jetbrains to create your first shared code module
