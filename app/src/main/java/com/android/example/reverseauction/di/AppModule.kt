package com.android.example.reverseauction.di

import com.android.example.reverseauction.navigation.NavigationProvider
import com.android.example.ui.navigation.HomeApiImpl
import com.android.example.ui.navigation.ProfileApiImpl
import com.core.feature_api.FeatureApi
import com.core.feature_api.HomeApi
import com.core.feature_api.ProfileApi
import com.feature.auction.ui.navigation.AuthApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideNavigationProvider(
        authApi: FeatureApi,
        homeApi: HomeApi,
        profileApi: ProfileApi
    ): NavigationProvider {
        return NavigationProvider(authApi, homeApi, profileApi)
    }

    @Provides
    fun provideAuthApi(): FeatureApi {
        return AuthApiImpl()
    }

    @Provides
    fun provideHomePageApi(): HomeApi {
        return HomeApiImpl()
    }

    @Provides
    fun provideProfilePageApi(): ProfileApi {
        return ProfileApiImpl()
    }
}
