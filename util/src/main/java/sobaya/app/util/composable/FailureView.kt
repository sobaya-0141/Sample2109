package sobaya.app.util.composable

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun FailureView(error: String) {
    Text(text = error)
}
