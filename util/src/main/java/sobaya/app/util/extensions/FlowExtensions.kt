package sobaya.app.util.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import sobaya.app.util.exception.NetworkException

fun <T> Flow<T>.setEvent(
    onStart: () -> Unit,
    onError: (e: NetworkException) -> Unit,
    onComplete: () -> Unit
): Flow<T> =
    onStart {
        onStart
    }.catch {
        if (it is NetworkException) {
            onError(it)
        } else {
            throw it
        }
    }.onCompletion {
        onComplete()
    }
