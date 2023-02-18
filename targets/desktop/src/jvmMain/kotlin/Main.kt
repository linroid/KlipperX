import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main(vararg arguments: String) = application {
    val fullscreen = arguments.isNotEmpty() && arguments[0] == "f"
    val windowState = rememberWindowState(
        width = 800.dp,
        height = 600.dp,
        placement = if (fullscreen) WindowPlacement.Fullscreen else WindowPlacement.Floating
    )
    Window(
        state = windowState,
        undecorated = fullscreen,
        onCloseRequest = ::exitApplication,
        title = "KlipperX"
    ) {
        window.rootPane.apply {
            // See the link below to get all properties
            // https://github.com/JetBrains/JetBrainsRuntime/blob/main/src/java.desktop/macosx/classes/sun/lwawt/macosx/CPlatformWindow.java#L145
            putClientProperty("apple.awt.fullWindowContent", true)
            // putClientProperty("apple.awt.transparentTitleBar", true)
            // putClientProperty("apple.awt.windowTitleVisible", false)
            // putClientProperty("apple.awt.transparentTitleBar", false)
            // putClientProperty("apple.awt.windowAppearance", "NSAppearanceNameVibrantDark")
            // putClientProperty("apple.awt.windowAppearance", "NSAppearanceNameVibrantLight")
        }
        Column {
            MainView()
            Text(arguments.contentToString())
        }
    }
}
