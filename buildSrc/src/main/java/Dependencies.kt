
object Versions {
    const val kotlin = "1.3.72"
    const val coroutines = "1.3.7"
    const val serialization = "0.20.0"
    const val kodein = "7.0.0"
    const val ktor = "1.3.2"
    const val sqlDelight = "1.3.0"
    const val napierVersion = "1.3.0"

}

object Android {
    const val compileSdkVersion = 29
    const val androidToolsVersion = "29.0.2"
    const val minSdk = 21
    const val targetSdk = 29
    const val junitVersion = "4.12"
}

object Ktor {
    val core = "io.ktor:ktor-client-core:${Versions.ktor}"
    val serialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"
    val androidClient = "io.ktor:ktor-client-android:${Versions.ktor}"
    val androidSerialization = "io.ktor:ktor-client-serialization-jvm:${Versions.ktor}"
}