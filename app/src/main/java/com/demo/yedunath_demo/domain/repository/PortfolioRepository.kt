package com.demo.yedunath_demo.domain.repository

import com.demo.yedunath_demo.domain.model.PortfolioDomain
import com.demo.yedunath_demo.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PortfolioRepository {
    fun getPortfolioData(): Flow<Resource<PortfolioDomain>>
}
