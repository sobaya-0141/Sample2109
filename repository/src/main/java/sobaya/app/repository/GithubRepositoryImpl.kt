package sobaya.app.repository

import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import sobaya.app.network.data_source.GithubDataSource
import sobaya.app.network.model.UserDetailResponse
import sobaya.app.network.model.UsersResponseItem
import sobaya.app.repository.model.User
import sobaya.app.repository.model.UserDetail
import sobaya.app.util.exception.NetworkException
import sobaya.app.util.extensions.setEvent
import sobaya.app.util.values.UserName

class GithubRepositoryImpl @Inject constructor(
    private val githubDataSource: GithubDataSource,
    private val coroutineDispatcher: CoroutineDispatcher
) : GithubRepository {
    override fun getUsers(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (e: NetworkException) -> Unit
    ): Flow<List<User>> = flow {
        emit(githubDataSource.getUsers().toListUser())
    }.setEvent(onStart, onError, onComplete).flowOn(coroutineDispatcher)

    override fun getUSerDetail(
        userName: UserName,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (e: NetworkException) -> Unit
    ): Flow<UserDetail> = flow {
        emit(githubDataSource.getUserDetail(userName).toUserDetail())
    }.setEvent(onStart, onError, onComplete).flowOn(coroutineDispatcher)
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
        name = this.name ?: "",
        followers = this.followers,
        following = this.following
    )
}
