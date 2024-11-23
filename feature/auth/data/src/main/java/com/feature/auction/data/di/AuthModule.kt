package com.feature.auction.data.di

import com.core.common.ErrorMapper
import com.feature.auction.data.AuthService
import com.feature.auction.data.repository.AuthRepoImpl
import com.feature.auction.domain.repository.AuthRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
interface AuthModule {

    @Binds
    fun authRepo(authRepoImpl: AuthRepoImpl): AuthRepo

    @Binds
    fun bindErrorMapper(impl: ErrorMapper.ErrorMapperImpl): ErrorMapper

    companion object {
        @Provides
        fun provideUserAuthorizedApi(retrofit: Retrofit): AuthService {
            return retrofit.create(AuthService::class.java)
        }
    }

}