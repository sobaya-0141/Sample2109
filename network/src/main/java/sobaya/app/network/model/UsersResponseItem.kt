package sobaya.app.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsersResponseItem(
    @SerialName("avatar_url") val avatar_url: String?,
    @SerialName("id") val id: Int,
    @SerialName("login") val login: String,
)
