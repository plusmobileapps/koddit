import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android

actual fun platformName(): String {
    return "Android"
}

actual val client: HttpClient = HttpClient(Android) {
}