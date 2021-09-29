package sobaya.app.network.data_source

import sobaya.app.network.model.UserDetailResponse
import sobaya.app.network.model.UsersResponseItem
import sobaya.app.util.values.UserName

interface GithubDataSource {
    suspend fun getUsers(): List<UsersResponseItem>
    suspend fun getUserDetail(user: UserName): UserDetailResponse
}
