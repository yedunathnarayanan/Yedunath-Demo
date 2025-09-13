package com.demo.yedunath_demo.data.mapper

import com.demo.yedunath_demo.TestDataFactory
import com.demo.yedunath_demo.data.model.PortfolioData
import com.demo.yedunath_demo.data.model.PortfolioResponse
import org.junit.Assert.*
import org.junit.Test

class HoldingMapperTest {

    @Test
    fun `toDomain should map HoldingResponse to HoldingDomain correctly`() {
        // Given
        val holdingResponse = TestDataFactory.createHoldingResponse(
            symbol = "AAPL",
            quantity = 100,
            ltp = 150.0,
            avgPrice = 140.0,
            close = 148.0
        )

        // When
        val result = holdingResponse.toDomain()

        // Then
        assertEquals("AAPL", result.symbol)
        assertEquals(100, result.quantity)
        assertEquals(150.0, result.ltp, 0.01)
        assertEquals(140.0, result.avgPrice, 0.01)
        assertEquals(148.0, result.close, 0.01)
    }

    @Test
    fun `toDomain should handle null values with defaults`() {
        // Given
        val holdingResponse = TestDataFactory.createHoldingResponse(
            symbol = null,
            quantity = null,
            ltp = null,
            avgPrice = null,
            close = null
        )

        // When
        val result = holdingResponse.toDomain()

        // Then
        assertEquals("", result.symbol)
        assertEquals(0, result.quantity)
        assertEquals(0.0, result.ltp, 0.01)
        assertEquals(0.0, result.avgPrice, 0.01)
        assertEquals(0.0, result.close, 0.01)
    }

    @Test
    fun `PortfolioResponse toDomain should map correctly`() {
        // Given
        val holdings = listOf(
            TestDataFactory.createHoldingResponse("AAPL", 100, 150.0, 140.0, 148.0),
            TestDataFactory.createHoldingResponse("GOOGL", 50, 2800.0, 2700.0, 2750.0)
        )
        val portfolioResponse = PortfolioResponse(
            data = PortfolioData(userHolding = holdings)
        )

        // When
        val result = portfolioResponse.toDomain()

        // Then
        assertEquals(2, result.holdings.size)
        assertEquals("AAPL", result.holdings[0].symbol)
        assertEquals("GOOGL", result.holdings[1].symbol)
    }

    @Test
    fun `PortfolioResponse toDomain should handle null data`() {
        // Given
        val portfolioResponse = PortfolioResponse(data = null)

        // When
        val result = portfolioResponse.toDomain()

        // Then
        assertTrue(result.holdings.isEmpty())
    }

    @Test
    fun `PortfolioResponse toDomain should handle null userHolding`() {
        // Given
        val portfolioResponse = PortfolioResponse(
            data = PortfolioData(userHolding = null)
        )

        // When
        val result = portfolioResponse.toDomain()

        // Then
        assertTrue(result.holdings.isEmpty())
    }

    @Test
    fun `PortfolioResponse toDomain should handle empty userHolding list`() {
        // Given
        val portfolioResponse = PortfolioResponse(
            data = PortfolioData(userHolding = emptyList())
        )

        // When
        val result = portfolioResponse.toDomain()

        // Then
        assertTrue(result.holdings.isEmpty())
    }
}
