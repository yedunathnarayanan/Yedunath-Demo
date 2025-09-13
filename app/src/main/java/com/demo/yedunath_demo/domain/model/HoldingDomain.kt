package com.demo.yedunath_demo.domain.model


data class HoldingDomain(
    val symbol: String,
    val quantity: Int,
    val ltp: Double,
    val avgPrice: Double,
    val close: Double,
)
data class PortfolioDomain(
    val holdings: List<HoldingDomain>
)

data class PortfolioCalculations(
    val totalCurrentValue: Double,
    val totalInvestment: Double,
    val totalPnl: Double,
    val totalTodaysPnl: Double,
    val totalPnlPercentage: Double
)

data class CalculatedPortfolio(
    val portfolio: PortfolioDomain,
    val calculations: PortfolioCalculations
)
