package com.android.example.data.di

import com.android.example.data.ProfileApiService
import com.android.example.data.repository.ProfileRepoImpl
import com.android.example.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    fun provideProfileApi(retrofit: Retrofit): ProfileApiService {
        return retrofit.create(ProfileApiService::class.java)
    }

    @Provides
    fun provideProfileRepository(
        profileApiService: ProfileApiService
    ): ProfileRepository = ProfileRepoImpl(profileApiService)
}