package sobaya.app.sample2021.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import sobaya.app.repository.GithubRepository

@HiltViewModel
class MainActivityViewModel @Inject constructor(): ViewModel()
