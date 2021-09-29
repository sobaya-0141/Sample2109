package sobaya.app.list.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun UserListView() {
    LazyColumn {
        item {
            (0..10).forEach {
                Text(it.toString())
            }
        }
    }
}
