package sobaya.app.repository

import kotlinx.coroutines.flow.Flow
import sobaya.app.repository.model.User
import sobaya.app.repository.model.UserDetail
import sobaya.app.util.exception.NetworkException
import sobaya.app.util.values.UserName

interface GithubRepository {
    fun getUsers(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (e: NetworkException) -> Unit
    ): Flow<List<User>>
    fun getUSerDetail(
        userName: UserName,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (e: NetworkException) -> Unit
    ): Flow<UserDetail>
}
