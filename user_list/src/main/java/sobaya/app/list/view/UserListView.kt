package sobaya.app.list.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import sobaya.app.composables.BoxTextField
import sobaya.app.composables.lv3_organisms.ListCard
import sobaya.app.repository.model.User
import sobaya.app.util.composable.FailureView
import sobaya.app.util.composable.LoadingView

@Composable
fun UserListView(
    navController: NavController,
    viewModel: UserListViewModel
) {
    val state = viewModel.uiState.value

    when {
        state.isLoading -> {
            LoadingView()
        }
        state.users != null -> {
            UserListSuccessView(users = state.users, navController = navController)
//            SamplePagingList(viewModel)
        }
        state.error != null -> {
            FailureView(error = state.error)
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
                ListCard(
                    onClick = { login ->
                        navController.navigate("userDetail/${login}")
                    },
                    login = it.login
                )
            }
            BoxTextField(state = mutableStateOf(TextFieldValue()))
        }
    }
}
