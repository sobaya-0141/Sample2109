package sobaya.app.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import sobaya.app.network.data_source.GithubDataSource
import sobaya.app.network.model.UserDetailResponse
import sobaya.app.network.model.UsersResponseItem
import sobaya.app.repository.model.User
import sobaya.app.repository.model.UserDetail
import sobaya.app.util.exception.NetworkException

class GithubRepositoryImpl @Inject constructor(
    private val githubDataSource: GithubDataSource
) : GithubRepository {
    override fun getUsers(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (e: NetworkException) -> Unit
    ): Flow<List<User>> = flow {
        emit(githubDataSource.getUsers().toListUser())
    }.catch {
        if (it is NetworkException) {
            onError(it)
        } else {
            throw it
        }
    }.onStart {
        onStart()
    }.onCompletion {
        onComplete()
    }

    override fun getUSerDetail(
        userName: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (e: NetworkException) -> Unit
    ): Flow<UserDetail> = flow {
        emit(githubDataSource.getUserDetail(userName).toUserDetail())
    }.onStart {
        onStart()
    }.catch {
        if (it is NetworkException) {
            onError(it)
        } else {
            throw it
        }
    }
}

fun List<UsersResponseItem>.toListUser(): List<User> {
    return this.map {
        User(
            imageUrl = it.avatar_url ?: "",
            login = it.login
        )
    }
}

fun UserDetailResponse.toUserDetail(): UserDetail {
    return UserDetail(
        imageUrl = this.avatar_url,
        login = this.login,
        name = this.name,
        followers = this.followers,
        following = this.following
    )
}
