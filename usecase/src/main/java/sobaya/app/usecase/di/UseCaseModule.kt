package sobaya.app.usecase.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import sobaya.app.usecase.GetUserDetailUseCase
import sobaya.app.usecase.GetUserDetailUseCaseImpl
import sobaya.app.usecase.GetUserListUseCase
import sobaya.app.usecase.GetUserListUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {
    @Provides
    fun provideGetUserDetailUseCase(
        getUserDetailUseCase: GetUserDetailUseCaseImpl
    ): GetUserDetailUseCase {
        return getUserDetailUseCase
    }

    @Provides
    fun provideGetUserListUseCase(
        getUserListUseCase: GetUserListUseCaseImpl
    ): GetUserListUseCase {
        return getUserListUseCase
    }
}
