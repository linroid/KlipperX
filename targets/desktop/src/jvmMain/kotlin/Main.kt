import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.linroid.klipperx.common.App
import java.awt.Frame

fun main() = application {
    Window(
        undecorated = true,
        onCloseRequest = ::exitApplication,
        title = "KlipperX"
    ) {
        window.extendedState = Frame.MAXIMIZED_BOTH
        window.rootPane.apply {
            putClientProperty("apple.awt.fullWindowContent", true)
            putClientProperty("apple.awt.transparentTitleBar", true)
        }
        App()
    }
}
