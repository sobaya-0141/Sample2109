package sobaya.app.network.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import sobaya.app.network.model.UserDetailResponse
import sobaya.app.network.model.UsersResponseItem

interface GithubService {
    @GET("users")
    suspend fun getUsers(): Response<List<UsersResponseItem>>

    @GET("users/{user}")
    suspend fun getUserDetail(@Path("user") user: String): Response<UserDetailResponse>
}
