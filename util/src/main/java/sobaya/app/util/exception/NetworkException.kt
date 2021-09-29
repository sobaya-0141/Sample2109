package sobaya.app.util.exception

class NetworkException(val code: Int, val errorBody: String) : Throwable()
