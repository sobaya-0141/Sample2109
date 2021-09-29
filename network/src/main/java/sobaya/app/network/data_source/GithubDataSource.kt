package sobaya.app.network.data_source

import sobaya.app.network.model.UserDetailResponse
import sobaya.app.network.model.UsersResponseItem

interface GithubDataSource {
    suspend fun getUsers(): List<UsersResponseItem>
    suspend fun getUserDetail(user: String): UserDetailResponse
}
