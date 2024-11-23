package com.core.network.di

import com.core.common.local.JwtTokenDataStore
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    @Named("userAuthorizedApi")
    fun provideUserAuthorizedApi(moshi: Moshi): ApiService {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/"
//                "https://rev-auc-g3-api-gateway-dev-424868328181.europe-west1.run.app/"
            )
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build().create(ApiService::class.java)
    }

    @Provides
    @Singleton
    @Named("profileApi")
    fun provideProfileApi(moshi: Moshi): ApiService {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/"
//                "https://rev-auc-g3-api-gateway-dev-424868328181.europe-west1.run.app/"
            )
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build().create(ApiService::class.java)
    }

    @Provides
    @Singleton
    @Named("auctionsApi")
    fun provideAuctionsApi(moshi: Moshi): ApiService {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/"
//                "https://rev-auc-g3-api-gateway-dev-424868328181.europe-west1.run.app/"
            )
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build().create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideInterceptor(
        jwtTokenDataStore: JwtTokenDataStore,
        @Named("userAuthorizedApi") apiService: ApiService
    ): AuthInterceptor {
        return AuthInterceptor(jwtTokenDataStore, apiService)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/"
//                "https://rev-auc-g3-api-gateway-dev-424868328181.europe-west1.run.app/"
            )
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(provideMoshi()))
            .build()
    }

}