package sobaya.app.list.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import sobaya.app.repository.GithubRepository
import sobaya.app.repository.model.User

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {
    val uiState: MutableState<UiState> = mutableStateOf(UiState.Loading)
    private val users = githubRepository.getUsers(
        onStart = {},
        onComplete = {},
        onError = {
            uiState.value = UiState.Failure(it.errorBody)
        }
    )

    init {
        users.onEach {
            uiState.value = UiState.Success(it)
        }.launchIn(viewModelScope)
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val users: List<User>) : UiState()
        data class Failure(val error: String) : UiState()
    }
}
