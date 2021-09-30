package sobaya.app.list.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
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
            UserListSuccessView(users = uiState.requireUser(), navController = navController)
        }
        is UserListViewModel.UiState.Failure -> {
            FailureView(error = uiState.requireError())
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
                TextButton(onClick = {
                    navController.navigate("userDetail/${it.login}")
                }) {
                    Text(it.login)
                }
            }
        }
    }
}

private fun UserListViewModel.UiState.requireUser(): List<User> {
    return (this as UserListViewModel.UiState.Success).users
}

private fun UserListViewModel.UiState.requireError(): String{
    return (this as UserListViewModel.UiState.Failure).error
}
