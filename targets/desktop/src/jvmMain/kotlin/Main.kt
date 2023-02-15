import androidx.compose.foundation.layout.Column
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        state = WindowState(width = 800.dp, height = 600.dp),
        undecorated = false,
        onCloseRequest = ::exitApplication,
        title = "KlipperX"
    ) {
        window.rootPane.apply {
            // See the link below to get all properties
            // https://github.com/JetBrains/JetBrainsRuntime/blob/main/src/java.desktop/macosx/classes/sun/lwawt/macosx/CPlatformWindow.java#L145
            putClientProperty("apple.awt.fullWindowContent", true)
            // putClientProperty("apple.awt.transparentTitleBar", false)
            // putClientProperty("apple.awt.windowAppearance", "NSAppearanceNameVibrantDark")
            // putClientProperty("apple.awt.windowAppearance", "NSAppearanceNameVibrantLight")
        }
        Column {
            MainView()
        }
    }
}
