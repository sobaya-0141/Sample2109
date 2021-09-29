package sobaya.app.network.data_source

import javax.inject.Inject
import sobaya.app.network.model.UserDetailResponse
import sobaya.app.network.model.UsersResponseItem
import sobaya.app.network.service.GithubService
import sobaya.app.util.exception.NetworkException

class GithubDataSourceImpl @Inject constructor(
    private val githubService: GithubService
): GithubDataSource {
    override suspend fun getUsers(): List<UsersResponseItem> {
        val response = githubService.getUsers()
        if (response.isSuccessful) {
            return requireNotNull(response.body())
        }
        throw NetworkException(response.code(), response.errorBody().toString())
    }

    override suspend fun getUserDetail(user: String): UserDetailResponse {
        val response = githubService.getUserDetail(user)
        if (response.isSuccessful) {
            return requireNotNull(response.body())
        }
        throw NetworkException(response.code(), response.errorBody().toString())
    }
}
