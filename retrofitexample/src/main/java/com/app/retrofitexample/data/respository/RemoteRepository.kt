package com.app.retrofitexample.data.respository

import com.app.retrofitexample.data.network.SafeApiRequest
import com.app.retrofitexample.data.models.Categories
import com.app.retrofitexample.data.models.CustomResult
import com.app.retrofitexample.data.network.Api
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val client: Api
) : SafeApiRequest() {
    suspend fun getCategories(): CustomResult<List<Categories>> {
        return when (val result = apiRequest { client.getCategories() }) {
            is CustomResult.Success -> {
                CustomResult.Success(result.data.data)
            }

            is CustomResult.Error -> {
                CustomResult.Error(result.exception)
            }
        }
    }

}
