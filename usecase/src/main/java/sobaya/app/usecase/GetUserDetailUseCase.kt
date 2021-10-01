package sobaya.app.usecase

import kotlinx.coroutines.flow.Flow
import sobaya.app.repository.model.UserDetail
import sobaya.app.util.exception.NetworkException
import sobaya.app.util.values.UserName

interface GetUserDetailUseCase {
    suspend operator fun invoke(
        userName: UserName,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (e: NetworkException) -> Unit
    ): Flow<UserDetail>
}
