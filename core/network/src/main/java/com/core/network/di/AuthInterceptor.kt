package com.core.network.di

import android.util.Log
import com.core.common.local.JwtTokenDataStore
import com.core.common.model.RefreshRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val jwtTokenDataStore: JwtTokenDataStore,
    private val refreshApi: ApiService
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return runBlocking {
            val accessToken = jwtTokenDataStore.getAccessJwt().firstOrNull().orEmpty()
            val refreshToken = jwtTokenDataStore.getRefreshJwt().firstOrNull().orEmpty()

            val requestBuilder = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")

//            if (accessToken.isNotEmpty()) {
//                requestBuilder.addHeader("Authorization", "Bearer $accessToken")
//            }

            var response = chain.proceed(requestBuilder.build())

            if (response.code == 401 || response.code == 403) {
                Log.d("Authorization", "here ${response.code}")
                val newAccessToken = refreshAccessToken(refreshToken)
                Log.d("Authorization", "here2")
                if (newAccessToken != null) {
                    val newRequest = requestBuilder
                        .removeHeader("Authorization")
                        .addHeader("Authorization", "Bearer $newAccessToken")
                        .build()
                    response = chain.proceed(newRequest)
                }
            }
            response
        }
    }

    private suspend fun storeNewLoginData(access: String, refresh: String) {
        withContext(Dispatchers.IO) {
            jwtTokenDataStore.apply {
                saveAccessJwt(access)
                saveRefreshJwt(refresh)
                decodeAccessToken(access)
                decodeRefreshToken(refresh)
            }
            Log.d("AuthInterceptor", "New tokens stored successfully")
        }
    }

    private suspend fun refreshAccessToken(refreshToken: String): String? {
        return withContext(Dispatchers.IO) {
            Log.d("refreshToken", refreshToken)
            if (refreshToken.isNotEmpty()) {
                try {
                    val refreshResponse = refreshApi.refreshToken(RefreshRequest(refreshToken))
                    if (refreshResponse.isSuccessful) {
                        refreshResponse.body()?.let {
                            it.refresh?.let { it1 ->
                                it.access?.let { it2 ->
                                    storeNewLoginData(
                                        it2,
                                        it1
                                    )
                                }
                            }
                            it.access
                        }
                    } else {
                        Log.d(
                            "AuthInterceptor",
                            "Token refresh failed: ${refreshResponse.errorBody()}"
                        )
                    }
                } catch (e: Exception) {
                    Log.d("AuthInterceptor", "Exception during token refresh: ${e.message}")
                }
            }
            null
        }
    }
}
