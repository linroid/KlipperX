import androidx.compose.runtime.Composable
import com.linroid.klipperx.NavigationHost
import com.linroid.klipperx.updateDarkMode

@Composable
fun MainView(hostMode: Boolean) {
    updateDarkMode()
    NavigationHost(hostMode)
}