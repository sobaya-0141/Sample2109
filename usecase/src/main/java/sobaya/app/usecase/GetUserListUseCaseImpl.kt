package sobaya.app.usecase

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import sobaya.app.repository.GithubRepository
import sobaya.app.repository.model.User
import sobaya.app.util.exception.NetworkException

class GetUserListUseCaseImpl @Inject constructor(
    private val repository: GithubRepository
) : GetUserListUseCase {
    override suspend fun invoke(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (e: NetworkException) -> Unit
    ): Flow<List<User>> {
        return repository.getUsers(onStart, onComplete, onError)
    }
}
