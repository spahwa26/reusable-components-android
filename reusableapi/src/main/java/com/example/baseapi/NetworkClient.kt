package com.example.baseapi

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class NetworkClient private constructor(
    private val apiInterface: BaseApiInterface,
    private val gson: Gson
) {

    companion object {
        private const val TIMEOUT = 30L

        fun create(
            baseUrl: String,
            debug: Boolean = false,
            gson: Gson = Gson()
        ): NetworkClient {
            val client = OkHttpClient.Builder().apply {
                connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                readTimeout(TIMEOUT, TimeUnit.SECONDS)

                if (debug) {
                    val logging = HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                    addInterceptor(logging)
                }
            }.build()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return NetworkClient(
                apiInterface = retrofit.create(BaseApiInterface::class.java),
                gson = gson
            )
        }
    }

    suspend fun <T> get(
        url: String,
        responseType: Class<T>,
        queries: Map<String, String?> = emptyMap(),
        headers: Map<String, String>? = null
    ): ApiResult<T> = withContext(Dispatchers.IO) {
        try {
            val response = apiInterface.get(url, queries, headers)
            handleResponse(response, responseType)
        } catch (e: IOException) {
            ApiResult.Exception(e)
        } catch (e: Exception) {
            ApiResult.Exception(e)
        }
    }

    suspend fun <T> postWithQueryAndForm(
        url: String,
        responseType: Class<T>,
        queries: Map<String, String?> = emptyMap(),
        fields: Map<String, String?> = emptyMap(),
        headers: Map<String, String>? = null
    ): ApiResult<T> = withContext(Dispatchers.IO) {
        try {
            val response = apiInterface.postWithQueryAndForm(url, queries, fields, headers)
            handleResponse(response, responseType)
        } catch (e: IOException) {
            ApiResult.Exception(e)
        } catch (e: Exception) {
            ApiResult.Exception(e)
        }
    }

    suspend fun <T> postWithQueryAndBody(
        url: String,
        responseType: Class<T>,
        queries: Map<String, String?> = emptyMap(),
        body: RequestBody? = null,
        headers: Map<String, String>? = null
    ): ApiResult<T> = withContext(Dispatchers.IO) {
        try {
            val response = apiInterface.postWithQueryAndBody(url, queries, body, headers)
            handleResponse(response, responseType)
        } catch (e: IOException) {
            ApiResult.Exception(e)
        } catch (e: Exception) {
            ApiResult.Exception(e)
        }
    }

    suspend fun <T> postMultipartWithQuery(
        url: String,
        responseType: Class<T>,
        queries: Map<String, String?> = emptyMap(),
        parts: Map<String, RequestBody?>,
        files: List<MultipartBody.Part> = emptyList(),
        headers: Map<String, String>? = null
    ): ApiResult<T> = withContext(Dispatchers.IO) {
        try {
            val response = apiInterface.postMultipartWithQuery(url, queries, parts, files, headers)
            handleResponse(response, responseType)
        } catch (e: IOException) {
            ApiResult.Exception(e)
        } catch (e: Exception) {
            ApiResult.Exception(e)
        }
    }

    private fun <T> handleResponse(response: Response<ResponseBody>, responseType: Class<T>): ApiResult<T> {
        return when {
            response.isSuccessful -> {
                val body = response.body()?.string()
                if (body != null) {
                    try {
                        ApiResult.Success(gson.fromJson(body, responseType))
                    } catch (e: Exception) {
                        ApiResult.Exception(e)
                    }
                } else {
                    ApiResult.Error(
                        code = response.code(),
                        message = "Response body is null"
                    )
                }
            }
            else -> {
                ApiResult.Error(
                    code = response.code(),
                    message = response.errorBody()?.string() ?: "Unknown error"
                )
            }
        }
    }
}

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val code: Int, val message: String) : ApiResult<Nothing>()
    data class Exception(val throwable: Throwable) : ApiResult<Nothing>()
}

// Extension functions
fun <T> ApiResult<T>.onSuccess(action: (T) -> Unit): ApiResult<T> {
    if (this is ApiResult.Success) {
        action(data)
    }
    return this
}

fun <T> ApiResult<T>.onError(action: (code: Int, message: String) -> Unit): ApiResult<T> {
    if (this is ApiResult.Error) {
        action(code, message)
    }
    return this
}

fun <T> ApiResult<T>.onException(action: (Throwable) -> Unit): ApiResult<T> {
    if (this is ApiResult.Exception) {
        action(throwable)
    }
    return this
}