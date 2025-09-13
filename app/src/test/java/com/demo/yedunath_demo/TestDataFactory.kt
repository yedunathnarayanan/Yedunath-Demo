package com.demo.yedunath_demo

import com.demo.yedunath_demo.data.model.HoldingResponse
import com.demo.yedunath_demo.data.model.PortfolioData
import com.demo.yedunath_demo.data.model.PortfolioResponse
import com.demo.yedunath_demo.domain.model.HoldingDomain
import com.demo.yedunath_demo.domain.model.PortfolioDomain

object TestDataFactory {
    
    fun createHoldingResponse(
        symbol: String? = "AAPL",
        quantity: Int? = 100,
        ltp: Double? = 150.0,
        avgPrice: Double? = 140.0,
        close: Double? = 148.0
    ) = HoldingResponse(
        symbol = symbol,
        quantity = quantity,
        ltp = ltp,
        avgPrice = avgPrice,
        close = close
    )
    
    fun createHoldingDomain(
        symbol: String = "AAPL",
        quantity: Int = 100,
        ltp: Double = 150.0,
        avgPrice: Double = 140.0,
        close: Double = 148.0
    ) = HoldingDomain(
        symbol = symbol,
        quantity = quantity,
        ltp = ltp,
        avgPrice = avgPrice,
        close = close
    )
    
    fun createPortfolioResponse(
        holdings: List<HoldingResponse> = listOf(createHoldingResponse())
    ) = PortfolioResponse(
        data = PortfolioData(userHolding = holdings)
    )
    
    fun createPortfolioDomain(
        holdings: List<HoldingDomain> = listOf(createHoldingDomain())
    ) = PortfolioDomain(holdings = holdings)
    
    fun createMultipleHoldings() = listOf(
        createHoldingDomain("AAPL", 100, 150.0, 140.0, 148.0),
        createHoldingDomain("GOOGL", 50, 2800.0, 2700.0, 2750.0),
        createHoldingDomain("MSFT", 75, 300.0, 280.0, 295.0)
    )
}
