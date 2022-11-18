package com.personal.finalproject

import com.personal.finalproject.api.ApiService
import com.personal.finalproject.models.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

object NewsHomeRepository {

    suspend fun fetchData(): Response {
        return ApiService.Network.build().getTopHeadlines().await()
    }
}