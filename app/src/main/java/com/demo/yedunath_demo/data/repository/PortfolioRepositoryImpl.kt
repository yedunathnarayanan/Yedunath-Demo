package com.demo.yedunath_demo.data.repository

import com.demo.yedunath_demo.data.mapper.toDomain
import com.demo.yedunath_demo.data.remote.ApiService
import com.demo.yedunath_demo.domain.model.PortfolioDomain
import com.demo.yedunath_demo.domain.repository.PortfolioRepository
import com.demo.yedunath_demo.utils.Constants
import com.demo.yedunath_demo.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PortfolioRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PortfolioRepository {

    override fun getPortfolioData(): Flow<Resource<PortfolioDomain>> = flow {
        try {
            emit(Resource.Loading())
            val response = apiService.getPortfolioData()
            if (response.isSuccessful) {
                response.body()?.let { portfolioResponse ->
                    emit(Resource.Success(portfolioResponse.toDomain()))
                } ?: emit(Resource.Error("Empty response"))
            } else {
                emit(Resource.Error("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }


}
