package sobaya.app.user_detail.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import sobaya.app.usecase.GetUserDetailUseCase
import sobaya.app.user_detail.model.UserDetail
import sobaya.app.util.values.UserName
import sobaya.lib.wasabi.WasabiState
import sobaya.wasabi.sobaya.app.user_detail.view.UserDetailViewModel.setState


@HiltViewModel
@sobaya.lib.wasabi.WasabiViewModel(UserDetailState::class)
class UserDetailViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase
) : ViewModel() {
    @WasabiState
    val uiState: MutableState<UserDetailState> = mutableStateOf(UserDetailState())

    fun getUserDetail(userName: UserName) {
        viewModelScope.launch {
            getUserDetailUseCase(
                userName = userName,
                onStart = {},
                onComplete = {},
                onError = { error ->
                    setState {
                        it.copy(
                            error = error.message
                        )
                    }
                }
            ).collect { detail ->
                setState {
                    it.copy(
                        userDetail = UserDetail.fromUserDetail(detail)
                    )
                }
            }
        }
    }
}

data class UserDetailState(
    val userDetail: UserDetail? = null,
    val error: String? = null
) {
    val isLoading = userDetail == null && error == null
}
