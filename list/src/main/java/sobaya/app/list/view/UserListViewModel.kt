package sobaya.app.list.view

import androidx.lifecycle.ViewModel
import javax.inject.Inject
import sobaya.app.repository.GithubRepository

class UserListViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {
    val users = githubRepository.getUsers(
        onStart = {

        },
        onComplete = {

        },
        onError = {

        }
    )
}
