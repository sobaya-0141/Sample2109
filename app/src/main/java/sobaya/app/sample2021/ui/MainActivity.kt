package sobaya.app.sample2021.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import sobaya.app.list.view.UserListView
import sobaya.app.sample2021.ui.theme.Sample2021Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.test()
        setContent {
            Sample2021Theme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    UserListView()
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewUserListView() {
    UserListView()
}
