package sobaya.app.user_detail.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import sobaya.app.usecase.GetUserDetailUseCase
import sobaya.app.user_detail.model.UserDetail
import sobaya.app.util.values.UserName
import kotlin.random.Random

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase
) : ViewModel() {
    val uiState: MutableState<UserDetailState> = mutableStateOf(UserDetailState())

    fun getUserDetail(userName: UserName) {
        uiState.value = uiState.value.copy(
            userDetail = UserDetail("", "", "")
        )
//        viewModelScope.launch {
//            getUserDetailUseCase(
//                userName = userName,
//                onStart = {},
//                onComplete = {},
//                onError = { error ->
//                    uiState.value = uiState.value.copy(
//                        error = error.message
//                    )
//                }
//            ).collect { detail ->
//                uiState.value = uiState.value.copy(
//                    userDetail = UserDetail.fromUserDetail(detail)
//                )
//            }
//        }
    }

    fun onClickLogin() {
        val userDetail = uiState.value.userDetail?.copy(
            login = "Login${Random.nextInt()}"
        )
        uiState.value = uiState.value.copy(
            userDetail = userDetail
        )
    }

    fun onClickName() {
        val userDetail = uiState.value.userDetail?.copy(
            name = "Name${Random.nextInt()}"
        )
        uiState.value = uiState.value.copy(
            userDetail = userDetail
        )
    }
}

data class UserDetailState(
    val userDetail: UserDetail? = null,
    val error: String? = null
) {
    val isLoading = userDetail == null && error == null
}
