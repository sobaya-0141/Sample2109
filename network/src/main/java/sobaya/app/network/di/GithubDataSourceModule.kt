package sobaya.app.network.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import sobaya.app.network.data_source.GithubDataSource
import sobaya.app.network.data_source.GithubDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
class GithubDataSourceModule {
    @Provides
    fun provideGithubDataSource(githubDataSource: GithubDataSourceImpl): GithubDataSource {
        return githubDataSource
    }
}
