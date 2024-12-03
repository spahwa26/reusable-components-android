package com.app.retrofitexample.data.respository

import com.app.retrofitexample.data.models.Categories
import com.app.retrofitexample.data.models.CustomResult
import javax.inject.Inject

class DefaultRepo @Inject constructor(
    private val remoteRepo: RemoteRepository
) {

    suspend fun getCategories(): CustomResult<List<Categories>> {
        return when (val result = remoteRepo.getCategories()) {
            is CustomResult.Success -> {
                CustomResult.Success(result.data)
            }
            is CustomResult.Error -> {
                CustomResult.Error(result.exception)
            }
        }
    }

}
