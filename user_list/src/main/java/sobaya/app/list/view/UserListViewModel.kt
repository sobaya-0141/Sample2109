package sobaya.app.list.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sobaya.app.list.view.UserListEvent.OnclickUser
import sobaya.app.repository.model.User
import sobaya.app.usecase.GetUserListUseCase

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase
) : ViewModel() {
    var uiState by mutableStateOf(UserListState())
        private set
    private val _uiEvent = MutableStateFlow(listOf<UserListEvent>())
    val uiEvent: StateFlow<List<UserListEvent>> = _uiEvent

    init {
        getUserList()
    }

    fun consume(target: UserListEvent) {
        val filtered = uiEvent.value.filterNot { it == target }
        _uiEvent.update { filtered }
    }

    fun onClickUser(login: String) {
        _uiEvent.update {
            it + OnclickUser(login)
        }
    }

    private fun getUserList() {
        viewModelScope.launch {
            getUserListUseCase(
                onStart = {},
                onComplete = {},
                onError = { error ->
                    uiState = uiState.copy(
                        error = error.errorBody
                    )
                }
            ).collect { users ->
                uiState = uiState.copy(
                    users = users
                )
            }
        }
    }
}

data class UserListState(
    val users: List<User>? = null,
    val error: String? = null
) {
    val isLoading = users == null && error == null
}

sealed interface UserListEvent {
    data class OnclickUser(val login: String) : UserListEvent
}
