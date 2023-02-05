import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.linroid.klipperx.common.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KlipperX"
    ) {
        App()
    }
}
