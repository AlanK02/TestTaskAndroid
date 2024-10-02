package com.alan.network.remote

import com.alan.core.models.Result
import retrofit2.Response
import retrofit2.http.GET


interface MainApi {

    @GET("u/0/uc?id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r")
    suspend fun getData(): Response<Result>

}