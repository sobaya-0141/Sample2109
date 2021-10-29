package sobaya.app.list.view

import androidx.compose.runtime.MutableState
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

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase
) : ViewModel() {
    val uiState: MutableState<UiState> = mutableStateOf(UiState.Loading)
    val samplePagingFlow: Flow<PagingData<String>> = Pager(
        PagingConfig(pageSize = 10, initialLoadSize = 10)
    ) {
        SamplePagingSource()
    }.flow.cachedIn(viewModelScope)

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
