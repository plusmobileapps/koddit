
object Versions {
    const val kotlin = "1.3.72"
    const val coroutines = "1.3.7"
    const val serialization = "0.20.0"
    const val koin = "3.0.0-alpha-2"
    const val ktor = "1.3.2"
    const val sqlDelight = "1.3.0"

}

object Ktor {
    val core = "io.ktor:ktor-client-core:${Versions.ktor}"
    val serialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"
    val androidClient = "io.ktor:ktor-client-android:${Versions.ktor}"
    val androidSerialization = "io.ktor:ktor-client-serialization-jvm:${Versions.ktor}"
}

object Koin {
    val core = "org.koin:koin-core:${Versions.koin}"
    val android = "org.koin:koin-android:${Versions.koin}"
    val androidViewModel = "org.koin:koin-android-viewmodel:${Versions.koin}"
}