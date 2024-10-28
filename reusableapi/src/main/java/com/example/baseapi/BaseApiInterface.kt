package com.example.baseapi

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface BaseApiInterface {

    @GET
    suspend fun get(
        @Url url: String,
        @QueryMap queries: Map<String, String?> = emptyMap(), // Nullable values in the query map
        @HeaderMap headers: Map<String, String>? = null // Optional headers map
    ): Response<ResponseBody>

    @POST
    @FormUrlEncoded
    suspend fun postWithQueryAndForm(
        @Url url: String,
        @QueryMap queries: Map<String, String?> = emptyMap(), // Nullable values in the query map
        @FieldMap fields: Map<String, String?> = emptyMap(), // Nullable values in the field map
        @HeaderMap headers: Map<String, String>? = null // Optional headers map
    ): Response<ResponseBody>

    @POST
    suspend fun postWithQueryAndBody(
        @Url url: String,
        @QueryMap queries: Map<String, String?> = emptyMap(), // Nullable values in the query map
        @Body body: RequestBody? = null, // Nullable body
        @HeaderMap headers: Map<String, String>? = null // Optional headers map
    ): Response<ResponseBody>

    @Multipart
    suspend fun postMultipartWithQuery(
        @Url url: String,
        @QueryMap queries: Map<String, String?> = emptyMap(), // Nullable values in the query map
        @PartMap parts: Map<String, @JvmSuppressWildcards RequestBody?>, // Nullable parts
        @Part files: List<MultipartBody.Part> = emptyList(),
        @HeaderMap headers: Map<String, String>? = null // Optional headers map
    ): Response<ResponseBody>
}
