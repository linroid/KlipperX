import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.linroid.klipperx.DiscoverScreen
import com.linroid.klipperx.KlipperXWindow

@Composable
fun MainView() {
    KlipperXWindow {
        DiscoverScreen(Modifier.fillMaxSize())
    }
}

