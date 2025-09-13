package com.demo.yedunath_demo.domain.usecase

import com.demo.yedunath_demo.domain.model.PortfolioCalculations
import com.demo.yedunath_demo.domain.model.CalculatedPortfolio
import com.demo.yedunath_demo.domain.model.PortfolioDomain
import javax.inject.Inject

class CalculatePortfolioUseCase @Inject constructor() {
    
    operator fun invoke(portfolio: PortfolioDomain): CalculatedPortfolio {
        val holdings = portfolio.holdings
        
        // Calculate portfolio totals directly from holdings
        val totalCurrentValue = holdings.sumOf { it.ltp * it.quantity }
        val totalInvestment = holdings.sumOf { it.avgPrice * it.quantity }
        val totalPnl = totalCurrentValue - totalInvestment
        val totalTodaysPnl = holdings.sumOf { (it.close - it.ltp) * it.quantity }
        val totalPnlPercentage = if (totalInvestment != 0.0) {
            (totalPnl / totalInvestment) * 100
        } else 0.0
        
        val calculations = PortfolioCalculations(
            totalCurrentValue = totalCurrentValue,
            totalInvestment = totalInvestment,
            totalPnl = totalPnl,
            totalTodaysPnl = totalTodaysPnl,
            totalPnlPercentage = totalPnlPercentage
        )
        
        return CalculatedPortfolio(
            portfolio = portfolio,
            calculations = calculations
        )
    }

}

