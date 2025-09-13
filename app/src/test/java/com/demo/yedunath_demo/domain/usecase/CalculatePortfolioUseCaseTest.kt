package com.demo.yedunath_demo.domain.usecase

import com.demo.yedunath_demo.TestDataFactory
import org.junit.Assert.*
import org.junit.Test

class CalculatePortfolioUseCaseTest {

    private val calculatePortfolioUseCase = CalculatePortfolioUseCase()

    @Test
    fun `invoke should calculate portfolio totals correctly for single holding`() {
        // Given
        val holding = TestDataFactory.createHoldingDomain(
            symbol = "AAPL",
            quantity = 100,
            ltp = 150.0,
            avgPrice = 140.0,
            close = 148.0
        )
        val portfolio = TestDataFactory.createPortfolioDomain(listOf(holding))

        // When
        val result = calculatePortfolioUseCase(portfolio)

        // Then
        val calculations = result.calculations
        assertEquals(15000.0, calculations.totalCurrentValue, 0.01) // 150 * 100
        assertEquals(14000.0, calculations.totalInvestment, 0.01) // 140 * 100
        assertEquals(1000.0, calculations.totalPnl, 0.01) // 15000 - 14000
        assertEquals(-200.0, calculations.totalTodaysPnl, 0.01) // (148 - 150) * 100
        assertEquals(7.14, calculations.totalPnlPercentage, 0.01) // (1000 / 14000) * 100
        assertEquals(portfolio, result.portfolio)
    }

    @Test
    fun `invoke should calculate portfolio totals correctly for multiple holdings`() {
        // Given
        val holdings = listOf(
            TestDataFactory.createHoldingDomain("AAPL", 100, 150.0, 140.0, 148.0),
            TestDataFactory.createHoldingDomain("GOOGL", 50, 2800.0, 2700.0, 2750.0),
            TestDataFactory.createHoldingDomain("MSFT", 75, 300.0, 280.0, 295.0)
        )
        val portfolio = TestDataFactory.createPortfolioDomain(holdings)

        // When
        val result = calculatePortfolioUseCase(portfolio)

        // Then
        val calculations = result.calculations
        assertEquals(177500.0, calculations.totalCurrentValue, 0.01) // 15000 + 140000 + 22500
        assertEquals(170000.0, calculations.totalInvestment, 0.01) // 14000 + 135000 + 21000
        assertEquals(7500.0, calculations.totalPnl, 0.01) // 177500 - 170000
        assertEquals(-3075.0, calculations.totalTodaysPnl, 0.01)
        assertEquals(4.411, calculations.totalPnlPercentage, 0.01)
    }

    @Test
    fun `invoke should handle zero investment correctly`() {
        // Given
        val holding = TestDataFactory.createHoldingDomain(
            quantity = 100,
            ltp = 150.0,
            avgPrice = 0.0,
            close = 148.0
        )
        val portfolio = TestDataFactory.createPortfolioDomain(listOf(holding))

        // When
        val result = calculatePortfolioUseCase(portfolio)

        // Then
        val calculations = result.calculations
        assertEquals(15000.0, calculations.totalCurrentValue, 0.01)
        assertEquals(0.0, calculations.totalInvestment, 0.01)
        assertEquals(15000.0, calculations.totalPnl, 0.01)
        assertEquals(0.0, calculations.totalPnlPercentage, 0.01) // Should be 0 when investment is 0
    }

    @Test
    fun `invoke should handle empty portfolio`() {
        // Given
        val portfolio = TestDataFactory.createPortfolioDomain(emptyList())

        // When
        val result = calculatePortfolioUseCase(portfolio)

        // Then
        val calculations = result.calculations
        assertEquals(0.0, calculations.totalCurrentValue, 0.01)
        assertEquals(0.0, calculations.totalInvestment, 0.01)
        assertEquals(0.0, calculations.totalPnl, 0.01)
        assertEquals(0.0, calculations.totalTodaysPnl, 0.01)
        assertEquals(0.0, calculations.totalPnlPercentage, 0.01)
    }

    @Test
    fun `invoke should handle negative P&L correctly`() {
        // Given
        val holding = TestDataFactory.createHoldingDomain(
            quantity = 100,
            ltp = 130.0, // Lower than avg price
            avgPrice = 140.0,
            close = 135.0
        )
        val portfolio = TestDataFactory.createPortfolioDomain(listOf(holding))

        // When
        val result = calculatePortfolioUseCase(portfolio)

        // Then
        val calculations = result.calculations
        assertEquals(13000.0, calculations.totalCurrentValue, 0.01)
        assertEquals(14000.0, calculations.totalInvestment, 0.01)
        assertEquals(-1000.0, calculations.totalPnl, 0.01) // Negative P&L
        assertEquals(500.0, calculations.totalTodaysPnl, 0.01) // (135 - 130) * 100
        assertEquals(-7.14, calculations.totalPnlPercentage, 0.01) // Negative percentage
    }
}
