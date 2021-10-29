package sobaya.app.list.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import sobaya.app.composables.BoxTextField
import sobaya.app.repository.model.User
import sobaya.app.util.composable.FailureView
import sobaya.app.util.composable.LoadingView

@Composable
fun UserListView(
    navController: NavController,
    viewModel: UserListViewModel
) {
    val uiState: UserListViewModel.UiState by viewModel.uiState

    when (uiState) {
        is UserListViewModel.UiState.Loading -> {
            LoadingView()
        }
        is UserListViewModel.UiState.Success -> {
//            UserListSuccessView(users = uiState.requireUser(), navController = navController)
            SamplePagingList(viewModel)
        }
        is UserListViewModel.UiState.Failure -> {
            FailureView(error = uiState.requireError())
        }
    }
}

@Composable
fun SamplePagingList(
    viewModel: UserListViewModel
) {
    val lazyPagingItems = viewModel.samplePagingFlow.collectAsLazyPagingItems()

    LazyColumn {
        items(lazyPagingItems) { sample ->
            Text(text = sample ?: "")
        }
    }
}

@Composable
fun UserListSuccessView(
    navController: NavController,
    users: List<User>
) {
    LazyColumn {
        item {
            users.forEach {
                Card(elevation = 10.dp, backgroundColor = Color.LightGray, modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)) {
                    Row {
                        Image(
                            painter = rememberImagePainter(
                                data = "https://avatars.githubusercontent.com/u/45986582?v=4",
                                builder = {
                                    transformations(CircleCropTransformation())
                                }
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .size(64.dp)
                        )
                        Column {
                            TextButton(onClick = {
                                navController.navigate("userDetail/${it.login}")
                            }) {
                                Text(it.login)
                            }
                            TextButton(onClick = {
                                navController.navigate("userDetail/${it.login}")
                            }) {
                                Text(it.login)
                            }
                        }
                    }
                }
            }
            BoxTextField(state = mutableStateOf(TextFieldValue()))
        }
    }
}

private fun UserListViewModel.UiState.requireUser(): List<User> {
    return (this as UserListViewModel.UiState.Success).users
}

private fun UserListViewModel.UiState.requireError(): String{
    return (this as UserListViewModel.UiState.Failure).error
}
