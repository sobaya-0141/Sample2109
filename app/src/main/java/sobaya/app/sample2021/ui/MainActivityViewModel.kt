package sobaya.app.sample2021.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import sobaya.app.repository.GithubRepository

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val githubRepository: GithubRepository
): ViewModel() {
    val test = githubRepository.getUsers(
        onComplete = {

        },
        onError = {

        },
        onStart = {

        }
    )

    fun test() {}

    init {
        this.test.onEach {
            println(it[0].login)
        }.launchIn(viewModelScope)
    }
}
