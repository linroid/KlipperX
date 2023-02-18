import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

fun main(vararg arguments: String) = application {
    val undecorated = arguments.isNotEmpty() && arguments[0] == "f"
    Window(
        state = WindowState(width = 800.dp, height = 600.dp),
        undecorated = undecorated,
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
