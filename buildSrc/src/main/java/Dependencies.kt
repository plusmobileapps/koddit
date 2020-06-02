object Versions {
    const val coroutines = "1.3.7"
    const val serialization = "0.20.0"
    const val koin = "3.0.0-alpha-2"
    const val ktor_version = "1.3.2"

}

object Ktor {
    val core = "io.ktor:ktor-client-core:${Versions.ktor_version}"
}

object Koin {
    val core = "org.koin:koin-core:${Versions.koin}"
    val android = "org.koin:koin-android:${Versions.koin}"
    val androidViewModel = "org.koin:koin-android-viewmodel:${Versions.koin}"
}