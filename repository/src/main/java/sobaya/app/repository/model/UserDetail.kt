package sobaya.app.repository.model

data class UserDetail(
    val imageUrl: String,
    val login: String,
    val name: String,
    val followers: Int,
    val following: Int
)
