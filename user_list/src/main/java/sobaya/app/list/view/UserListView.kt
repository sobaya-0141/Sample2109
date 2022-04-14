package sobaya.app.list.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import sobaya.app.composables.BoxTextField
import sobaya.app.composables.lv3_organisms.ListCard
import sobaya.app.list.view.UserListEvent.OnclickUser

@Composable
fun UserListView(
    navController: NavController,
    viewModel: UserListViewModel
) {
    val state: UserListState = viewModel.uiState
    val events: State<List<UserListEvent>> = viewModel.uiEvent.collectAsState()

    UserListView(
        state = state,
        events = events.value,
        consume = { event -> viewModel.consume(event) },
        onClickUser = { login -> viewModel.onClickUser(login) }
    )

    events.value.forEach {
        when (it) {
            is OnclickUser -> {
                navController.navigate("userDetail/${it.login}")
            }
        }
        viewModel.consume(it)
    }
}


@Composable
fun UserListView(
    state: UserListState,
    events: List<UserListEvent>,
    consume: (e: UserListEvent) -> Unit,
    onClickUser: (login: String) -> Unit
) {
    LazyColumn {
        item {
            state.users?.forEach {
                ListCard(
                    onClick = { login ->
                        onClickUser(login)
                    },
                    login = it.login
                )
            }
            BoxTextField(state = mutableStateOf(TextFieldValue()))
        }
    }
}
