package com.core.common.local

import android.util.Log
import androidx.datastore.preferences.core.edit
import javax.inject.Inject

class ActivityUseCase @Inject constructor(private val dataStore: JwtTokenDataStore) {
    suspend fun saveAccessJwt(token: String) {
        return dataStore.saveAccessJwt(token)
    }

    suspend fun saveRefreshJwt(token: String) {
       return dataStore.saveRefreshJwt(token)
    }

    suspend fun decodeAccessToken(accessToken: String){
        return dataStore.decodeAccessToken(accessToken)
    }

    suspend fun decodeRefreshToken(refreshToken: String) {
        return dataStore.decodeRefreshToken(refreshToken)
    }

    suspend fun saveUserInfo(refreshToken: String, accessToken: String){
        return dataStore.saveUserInfo(refreshToken,accessToken)
    }
}