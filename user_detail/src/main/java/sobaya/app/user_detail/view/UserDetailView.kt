package sobaya.app.user_detail.view

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import sobaya.app.repository.model.UserDetail
import sobaya.app.util.composable.FailureView
import sobaya.app.util.composable.LoadingView
import sobaya.app.util.values.UserName

@Composable
fun UserDetailView(
    userName: String,
    viewModel: UserDetailViewModel
) {
    val uiState: UserDetailViewModel.UiState by viewModel.uiState
    viewModel.getUserDetail(UserName(userName))

    when (uiState) {
        is UserDetailViewModel.UiState.Loading -> {
            LoadingView()
        }
        is UserDetailViewModel.UiState.Success -> {
            UserDetailSuccessView(userDetail = uiState.requireUserDetail())
        }
        is UserDetailViewModel.UiState.Failure -> {
            FailureView(error = uiState.requireError())
        }
    }
}

@Composable
fun UserDetailSuccessView(userDetail: UserDetail) {
    Text(text = userDetail.name)
}

private fun UserDetailViewModel.UiState.requireUserDetail(): UserDetail {
    return (this as UserDetailViewModel.UiState.Success).userDetail
}

private fun UserDetailViewModel.UiState.requireError(): String{
    return (this as UserDetailViewModel.UiState.Failure).error
}
