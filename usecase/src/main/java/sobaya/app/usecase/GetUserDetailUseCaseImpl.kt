package sobaya.app.usecase

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import sobaya.app.repository.GithubRepository
import sobaya.app.repository.model.UserDetail
import sobaya.app.util.exception.NetworkException
import sobaya.app.util.values.UserName

class GetUserDetailUseCaseImpl @Inject constructor(
    private val repository: GithubRepository
) : GetUserDetailUseCase {
    override suspend operator fun invoke(
        userName: UserName,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (e: NetworkException) -> Unit
    ): Flow<UserDetail> =
        repository.getUSerDetail(userName, onStart, onComplete, onError)
}
