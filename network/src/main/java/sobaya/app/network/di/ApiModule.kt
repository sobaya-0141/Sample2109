package sobaya.app.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import sobaya.app.network.service.GithubService

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideGithubService(retrofit: Retrofit): GithubService =
        retrofit.create(GithubService::class.java)

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()

        logging.level = if (true) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        builder.addInterceptor(logging)

        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.github.com/")
            .addConverterFactory(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    allowStructuredMapKeys = true
                    prettyPrint = true
                    coerceInputValues = true
                }.asConverterFactory(
                    "application/json".toMediaType()
                ),
            )
            .build()
}
