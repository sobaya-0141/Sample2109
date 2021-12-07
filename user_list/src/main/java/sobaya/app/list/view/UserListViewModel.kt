package sobaya.app.list.view

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import sobaya.app.repository.model.User
import sobaya.app.repository.paging.SamplePagingSource
import sobaya.app.usecase.GetUserListUseCase
import sobaya.lib.wasabi.WasabiState
import sobaya.wasabi.sobaya.app.list.view.UserListViewModel.setState

@HiltViewModel
@sobaya.lib.wasabi.WasabiViewModel(UserListState::class)
class UserListViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase
) : ViewModel() {
    @WasabiState
    val uiState = mutableStateOf(UserListState())

    init {
        getUserList()
    }

    private fun getUserList() {
        viewModelScope.launch {
            getUserListUseCase(
                onStart = {},
                onComplete = {},
                onError = { error ->
                    setState {
                        it.copy(error = error.errorBody)
                    }
                }
            ).collect { users ->
                setState {
                    it.copy(
                        users = users
                    )
                }
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
