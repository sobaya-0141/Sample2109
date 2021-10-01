package sobaya.app.list.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import sobaya.app.repository.GithubRepository
import sobaya.app.repository.model.User
import sobaya.app.usecase.GetUserListUseCase

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase
) : ViewModel() {
    val uiState: MutableState<UiState> = mutableStateOf(UiState.Loading)

    init {
        getUserList()
    }

    private fun getUserList() {
        viewModelScope.launch {
            getUserListUseCase(
                onStart = {},
                onComplete = {},
                onError = {
                    uiState.value = UiState.Failure(it.errorBody)
                }
            ).collect {
                uiState.value = UiState.Success(it)
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val users: List<User>) : UiState()
        data class Failure(val error: String) : UiState()
    }
}
