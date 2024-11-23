package com.core.common.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import com.auth0.android.jwt.JWT
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JwtTokenDataStore @Inject constructor(
    @ApplicationContext val context: Context
) {

    private val Context.dataStore by preferencesDataStore("TokenDataStore")
    private val ACCESS_TOKEN = stringPreferencesKey("AccessToken")
    private val REFRESH_TOKEN = stringPreferencesKey("RefreshToken")
    private val IS_AUTHORIZED_ = booleanPreferencesKey("Authorized")
//    private val USER_TYPE_ = stringPreferencesKey("UserType")


//    private val ACCESS_TOKEN_EXP = stringPreferencesKey(JWT_ACCESS_EXP)
//    private val ACCESS_TOKEN_ISSUE = stringPreferencesKey(JWT_ACCESS_ISSUE)
//    private val REFRESH_TOKEN_EXP = stringPreferencesKey(JWT_REFRESH_EXP)
//    private val REFRESH_TOKEN_ISSUE = stringPreferencesKey(JWT_REFRESH_ISSUE)



    suspend fun saveAccessJwt(token: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = token
        }
    }

    suspend fun saveRefreshJwt(token: String) {
        context.dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN] = token
        }
    }



    suspend fun decodeAccessToken(accessToken: String) {
        val jwt = JWT(accessToken)
        val accessTokenExp = jwt.getClaim("exp").asLong()
        val accessTokenIssueTime = jwt.getClaim("iat").asLong()
        val twoFactorActivated = jwt.getClaim("two_factor_authentication_activated").asBoolean()
        val isVerified = jwt.getClaim("is_verified").asString()
        val userType = jwt.getClaim("user_type").asString()
        val password = jwt.getClaim("password").asString()
//        val isSocialAccount = jwt.getClaim("is_social_account").asBoolean()
        val userId = jwt.getClaim("user_id").asString()
//        val userProfileType = jwt.getClaim("user_profile_type").asString()
        Log.d(
            "refreshAccessToken",
            "decodeAccessToken $accessTokenExp \n $accessTokenIssueTime \n twoFactorActivated  $twoFactorActivated " +
                    " \n isVerified  $isVerified \n password $password"
        )

        Log.d("decodeAccessToken", userId.toString())
        context.dataStore.edit { preferences ->
//            preferences[ACCESS_TOKEN_EXP] = accessTokenExp.toString()
//            preferences[ACCESS_TOKEN_ISSUE] = accessTokenIssueTime.toString()
//            preferences[TWO_FACTOR_VERIFIED_] = twoFactorActivated.toString().toBoolean()
//            preferences[USER_TYPE_] = userType.toString()
//            preferences[USER_ID_] = userId.toString()
//            preferences[IS_SOCIAL_ACCOUNT_] = isSocialAccount.toString().toBoolean()
//            preferences[IS_VERIFIED_] = isVerified.toString()
            preferences[IS_AUTHORIZED_] = true
//            preferences[IS_COMPANY_] = (userProfileType.toString() == "Company")
        }
    }

    suspend fun decodeRefreshToken(refreshToken: String) {
        val jwt = JWT(refreshToken)
        val refreshTokenExp = jwt.getClaim("exp").asLong()
        val refreshTokenIssueTime = jwt.getClaim("iat").asLong()
        Log.d("refreshAccessToken", "decodeAccessToken $refreshTokenExp  $refreshTokenIssueTime")
//        context.dataStore.edit { preferences ->
//            preferences[REFRESH_TOKEN_EXP] = refreshTokenExp.toString()
//            preferences[REFRESH_TOKEN_ISSUE] = refreshTokenIssueTime.toString()
//        }
    }


//    fun getRefreshTokenExp(): Flow<String?> {
//        return context.dataStore.data.map { preferences ->
//            preferences[REFRESH_TOKEN_EXP]
//        }
//    }
//
//    fun getRefreshTokenIssueTime(): Flow<String?> {
//        return context.dataStore.data.map { preferences ->
//            preferences[REFRESH_TOKEN_ISSUE]
//        }
//    }
//
//    fun getAccessTokenExp(): Flow<String?> {
//        return context.dataStore.data.map { preferences ->
//            preferences[ACCESS_TOKEN_EXP]
//        }
//    }
//
//    fun getAccessTokenIssueTime(): Flow<String?> {
//        return context.dataStore.data.map { preferences ->
//            preferences[ACCESS_TOKEN_ISSUE]
//        }
//    }

    fun getAccessJwt(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN]
        }
    }

    fun getRefreshJwt(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN]
        }
    }

    suspend fun saveUserInfo(refreshToken: String, accessToken: String) {
        saveRefreshJwt(refreshToken)
        saveAccessJwt(accessToken)
        decodeAccessToken(accessToken)
        decodeRefreshToken(refreshToken)
    }

    suspend fun clearAllTokens() {
        context.dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN)
//            preferences.remove(SOCIAL_STATUS_)
//            preferences.remove(REFRESH_TOKEN)
//            preferences.remove(ACCESS_TOKEN_ISSUE)
//            preferences.remove(ACCESS_TOKEN_EXP)
//            preferences.remove(REFRESH_TOKEN_EXP)
//            preferences.remove(REFRESH_TOKEN_ISSUE)
        }
    }


    suspend fun getUserProfileFromAccessToken(): UserProfile? {
        val accessToken = getAccessJwt().firstOrNull()
        Log.d("accessToken", accessToken.toString())
        accessToken?.let {
            val jwt = JWT(it)
            val password = jwt.getClaim("password").asString() ?: ""
            val profileName = jwt.getClaim("username").asString() ?: "User"
            val isVerified = jwt.getClaim("is_verified").asBoolean() ?: false
            val role = jwt.getClaim("user_type").asString() ?: ""
            Log.d("role 1", profileName)
            return UserProfile(profileName, isVerified, password, role)
        }
        return null
    }
}


data class UserProfile(
    val profileName: String,
    val isVerified: Boolean,
    val password: String,
    val role: String
)