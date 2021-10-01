package sobaya.app.usecase

import kotlinx.coroutines.flow.Flow
import sobaya.app.repository.model.User
import sobaya.app.util.exception.NetworkException

interface GetUserListUseCase {
    suspend operator fun invoke(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (e: NetworkException) -> Unit
    ): Flow<List<User>>
}
