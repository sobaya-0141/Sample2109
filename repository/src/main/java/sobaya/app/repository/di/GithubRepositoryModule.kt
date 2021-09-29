package sobaya.app.repository.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sobaya.app.repository.GithubRepository
import sobaya.app.repository.GithubRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
class GithubRepositoryModule {

    @Provides
    fun provideGithubRepository(githubRepository: GithubRepositoryImpl): GithubRepository {
        return githubRepository
    }
}
