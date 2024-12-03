package com.app.retrofitexample.data.network

import com.app.retrofitexample.data.models.Categories
import com.app.retrofitexample.data.models.CommonResponse
import retrofit2.Response
import retrofit2.http.GET


//TODO: these are just sample calls for demonstration, replace them with your own
interface Api {

    @GET("categories")
    suspend fun getCategories(): Response<CommonResponse<List<Categories>>>

}
