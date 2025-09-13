package com.demo.yedunath_demo.presentation.adapter

import com.demo.yedunath_demo.TestDataFactory
import org.junit.Assert.*
import org.junit.Test

class HoldingDiffCallbackTest {

    private val diffCallback = HoldingDiffCallback()

    @Test
    fun `areItemsTheSame should return true for same symbol`() {
        // Given
        val holding1 = TestDataFactory.createHoldingDomain(symbol = "AAPL")
        val holding2 = TestDataFactory.createHoldingDomain(symbol = "AAPL", ltp = 200.0)

        // When
        val result = diffCallback.areItemsTheSame(holding1, holding2)

        // Then
        assertTrue(result)
    }

    @Test
    fun `areItemsTheSame should return false for different symbols`() {
        // Given
        val holding1 = TestDataFactory.createHoldingDomain(symbol = "AAPL")
        val holding2 = TestDataFactory.createHoldingDomain(symbol = "GOOGL")

        // When
        val result = diffCallback.areItemsTheSame(holding1, holding2)

        // Then
        assertFalse(result)
    }

    @Test
    fun `areContentsTheSame should return true for identical holdings`() {
        // Given
        val holding1 = TestDataFactory.createHoldingDomain(
            symbol = "AAPL",
            quantity = 100,
            ltp = 150.0,
            avgPrice = 140.0,
            close = 148.0
        )
        val holding2 = TestDataFactory.createHoldingDomain(
            symbol = "AAPL",
            quantity = 100,
            ltp = 150.0,
            avgPrice = 140.0,
            close = 148.0
        )

        // When
        val result = diffCallback.areContentsTheSame(holding1, holding2)

        // Then
        assertTrue(result)
    }

    @Test
    fun `areContentsTheSame should return false for different content`() {
        // Given
        val holding1 = TestDataFactory.createHoldingDomain(ltp = 150.0)
        val holding2 = TestDataFactory.createHoldingDomain(ltp = 160.0)

        // When
        val result = diffCallback.areContentsTheSame(holding1, holding2)

        // Then
        assertFalse(result)
    }

    @Test
    fun `areContentsTheSame should return false for different quantities`() {
        // Given
        val holding1 = TestDataFactory.createHoldingDomain(quantity = 100)
        val holding2 = TestDataFactory.createHoldingDomain(quantity = 200)

        // When
        val result = diffCallback.areContentsTheSame(holding1, holding2)

        // Then
        assertFalse(result)
    }
}
