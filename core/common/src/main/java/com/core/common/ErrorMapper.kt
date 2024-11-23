package com.core.common
import android.util.Log
import retrofit2.HttpException
import javax.inject.Inject
interface ErrorMapper {
    operator fun invoke(exception: Throwable): Throwable
    class ErrorMapperImpl @Inject constructor() : ErrorMapper {
        override fun invoke(exception: Throwable): Throwable = when (exception) {
            is HttpException -> try {
                val errorBody = exception.response()?.errorBody()?.string()
                if (errorBody != null) {
                    if(exception.code() == 403) {
                        val fields = errorBody.trim().removePrefix("{").removeSuffix("}").split(",")
                        val userId = fields.first { it.contains("user_id") }.split(":")[1].trim()
                            .removeSurrounding("\"")
                        SignInVerificationPendingException(userId)
                    }else if(exception.code() == 401) {
                        SignInVerificationPendingException("401")
                    }else if(exception.code() == 400) {
                        SignInVerificationPendingException("400")
                    }else SignInVerificationPendingException("")
                } else {
                    SignInVerificationPendingException(exception.message.orEmpty())
                }
            } catch (e: Exception) {
                Log.e("ErrorMapper", "Error parsing the response", e)
                SignInVerificationPendingException(exception.message.orEmpty())
            }
            else -> exception
        }
    }
}
class SignInVerificationPendingException(e: String) : Exception(e)