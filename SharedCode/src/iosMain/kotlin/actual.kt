import io.ktor.client.HttpClient
import io.ktor.client.engine.ios.Ios
import platform.UIKit.UIDevice

actual fun platformName(): String {
    return UIDevice.currentDevice.systemName() +
            " " +
            UIDevice.currentDevice.systemVersion
}

actual val client: HttpClient = HttpClient(Ios)