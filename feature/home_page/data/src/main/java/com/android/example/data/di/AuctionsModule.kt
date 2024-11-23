package com.android.example.data.di
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

import com.android.example.data.AuctionsApiService
import com.android.example.data.repository.AuctionsRepoImpl
import com.android.example.domain.repository.AuctionsRepository

@Module
@InstallIn(SingletonComponent::class)
object AuctionsModule {
    @Provides
    fun provideAuctionsApi(retrofit: Retrofit): AuctionsApiService {
        return retrofit.create(AuctionsApiService::class.java)
    }

    @Provides
    fun provideAuctionsRepository(
        auctionsApiService: AuctionsApiService
    ): AuctionsRepository = AuctionsRepoImpl(auctionsApiService)
}