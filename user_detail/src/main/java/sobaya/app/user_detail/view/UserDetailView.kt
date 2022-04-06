package sobaya.app.user_detail.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import sobaya.app.user_detail.model.UserDetail
import sobaya.app.util.composable.FailureView
import sobaya.app.util.composable.LoadingView
import sobaya.app.util.values.UserName
import kotlin.random.Random
import kotlin.random.Random.Default

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
            UserDetailSuccessView(
                userDetail = uiState.userDetail,
                onClickLogin = { viewModel.onClickLogin() },
                onClickName = { viewModel.onClickName() }
            )
        }
        uiState.error != null -> {
            FailureView(error = uiState.error)
        }
    }
}

var i = 0

@Composable
fun UserDetailSuccessView(
    userDetail: UserDetail,
    onClickLogin: () -> Unit,
    onClickName: () -> Unit
) {
    LaunchedEffect(Random.nextInt()) {
        val a = ++i
        while (true) {
            println("TEST::a${a}")
            delay(500)
        }
    }
    Column {
        Box {
            LaunchedEffect(Random.nextInt()) {
                val b = ++i
                while (true) {
                    println("TEST::b${b}")
                    delay(500)
                }
            }
            Text(text = userDetail.login)
        }
        Box {
            Text(text = userDetail.name)
            LaunchedEffect(Random.nextInt()) {
                val c = ++i
                while (true) {
                    println("TEST::c${c}")
                    delay(500)
                }
            }
        }
    }
    Row {
        Button(onClick = onClickLogin) {
            Text(text = "Login")
        }
        Button(onClick = onClickName) {
            Text(text = "Name")
        }
    }
}
