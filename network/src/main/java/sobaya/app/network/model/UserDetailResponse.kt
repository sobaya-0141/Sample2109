package sobaya.app.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDetailResponse(
    @SerialName("avatar_url") val avatar_url: String,
    @SerialName("followers") val followers: Int,
    @SerialName("following") val following: Int,
    @SerialName("login") val login: String,
    @SerialName("name") val name: String?
)
