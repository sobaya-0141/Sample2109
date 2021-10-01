package sobaya.app.usecase.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import sobaya.app.usecase.GetUserDetailUseCase
import sobaya.app.usecase.GetUserDetailUseCaseImple
import sobaya.app.usecase.GetUserListUseCase
import sobaya.app.usecase.GetUserListUseCaseImple

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {
    @Provides
    fun provideGetUserDetailUseCase(
        getUserDetailUseCase: GetUserDetailUseCaseImple
    ): GetUserDetailUseCase {
        return getUserDetailUseCase
    }

    @Provides
    fun provideGetUserListUseCase(
        getUserListUseCase: GetUserListUseCaseImple
    ): GetUserListUseCase {
        return getUserListUseCase
    }
}
