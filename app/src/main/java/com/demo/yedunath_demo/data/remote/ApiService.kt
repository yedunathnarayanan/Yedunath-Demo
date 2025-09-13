package com.demo.yedunath_demo.data.remote

import com.demo.yedunath_demo.data.model.PortfolioResponse
import com.demo.yedunath_demo.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(".")
    suspend fun getPortfolioData(): Response<PortfolioResponse>
}
