package sobaya.app.user_detail.view

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import sobaya.app.usecase.GetUserDetailUseCase
import sobaya.lib.wasabi.WasabiState
import sobaya.wasabi.sobaya.app.user_detail.view.UserDetailViewModel.setState


@HiltViewModel
@sobaya.lib.wasabi.WasabiViewModel(TestDataClass::class)
class UserDetailViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase
) : ViewModel() {
    @WasabiState
    val testFieldName: MutableState<TestDataClass> = mutableStateOf(TestDataClass(""))

    init {
        setState { it.copy("test") }
    }

//    fun getUserDetail(userName: UserName) {
//        viewModelScope.launch {
//            getUserDetailUseCase(
//                userName = userName,
//                onStart = {},
//                onComplete = {},
//                onError = {
//                    uiState.value = UiState.Failure(it.errorBody)
//                }
//            ).collect {
//                uiState.value = UiState.Success(it)
//            }
//        }
//    }

//    sealed class UiState {
//        object Loading : UiState()
//        data class Success(val userDetail: UserDetail) : UiState()
//        data class Failure(val error: String) : UiState()
//    }
}

data class TestDataClass(
    val test: String
)
