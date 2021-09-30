package sobaya.app.user_detail.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import sobaya.app.repository.GithubRepository
import sobaya.app.repository.model.UserDetail
import sobaya.app.user_detail.view.UserDetailViewModel.UiState.Loading
import sobaya.app.util.values.UserName

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {
    val uiState: MutableState<UiState> = mutableStateOf(Loading)

    fun getUserDetail(userName: UserName) {
        viewModelScope.launch {
            val userDetail = repository.getUSerDetail(
                userName = userName,
                onStart = {},
                onComplete = {},
                onError = {
                    uiState.value = UiState.Failure(it.errorBody)
                }
            ).firstOrNull()
            userDetail?.let {
                uiState.value = UiState.Success(it)
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val userDetail: UserDetail) : UiState()
        data class Failure(val error: String) : UiState()
    }
}
