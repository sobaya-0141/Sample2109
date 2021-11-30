package sobaya.app.user_detail.view

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import sobaya.app.repository.model.UserDetail
import sobaya.app.util.composable.FailureView
import sobaya.app.util.composable.LoadingView
import sobaya.app.util.values.UserName

@Composable
fun UserDetailView(
    userName: String,
    viewModel: UserDetailViewModel
) {
    val uiState = viewModel.uiState.value
    viewModel.getUserDetail(UserName(userName))

    when  {
        uiState.isLoading -> {
            LoadingView()
        }
        uiState.userDetail != null -> {
            UserDetailSuccessView(userDetail = uiState.userDetail)
        }
        uiState.error != null -> {
            FailureView(error = uiState.error)
        }
    }
}

@Composable
fun UserDetailSuccessView(userDetail: UserDetail) {
    Text(text = userDetail.name)
}
