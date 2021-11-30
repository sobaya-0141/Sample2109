package sobaya.app.user_detail.model

import sobaya.app.repository.model.UserDetail

data class UserDetail(
    val imageUrl: String,
    val login: String,
    val name: String
) {
    companion object {
        fun fromUserDetail(detail: UserDetail): sobaya.app.user_detail.model.UserDetail {
            return UserDetail(
                imageUrl = detail.imageUrl,
                login = detail.login,
                name = detail.name
            )
        }
    }
}
