package com.core.common
import retrofit2.HttpException
import retrofit2.Response
inline fun <T, R> callAndMap(
    serviceCall: () -> Response<T>,
    mapper: (T) -> R
): Result<R> {
    return try {
        val response = serviceCall()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            val mappedResult = mapper(body)
            Result.success(mappedResult)
        } else if (response.code() == 403) {
            Result.failure(HttpException(response))
        }else if(response.code() == 401){
            Result.failure(HttpException(response))
        }else if(response.code() == 400){
            Result.failure(HttpException(response))
        } else {
            Result.failure(Exception("Network call failed with code: ${response.code()}"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}
inline fun <T> Result<T>.mapError(transformer: (exception: Throwable) -> Throwable): Result<T> {
    return when {
        isSuccess -> Result.success(getOrThrow())
        else -> Result.failure(transformer(exceptionOrNull()!!))
    }
}