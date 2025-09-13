package com.demo.yedunath_demo.domain.usecase

import com.demo.yedunath_demo.domain.model.PortfolioDomain
import com.demo.yedunath_demo.domain.repository.PortfolioRepository
import com.demo.yedunath_demo.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPortfolioDataUseCase @Inject constructor(
    private val repository: PortfolioRepository
) {
    operator fun invoke(): Flow<Resource<PortfolioDomain>> {
        return repository.getPortfolioData()
    }
}
