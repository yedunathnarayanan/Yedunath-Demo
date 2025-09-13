package com.demo.yedunath_demo.data.repository

import app.cash.turbine.test
import com.demo.yedunath_demo.TestDataFactory
import com.demo.yedunath_demo.data.remote.ApiService
import com.demo.yedunath_demo.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Response

class PortfolioRepositoryImplTest {

    private val apiService = mockk<ApiService>()
    private val repository = PortfolioRepositoryImpl(apiService)

    @Test
    fun `getPortfolioData should emit Loading then Success when API call succeeds`() = runTest {
        // Given
        val portfolioResponse = TestDataFactory.createPortfolioResponse()
        val response = Response.success(portfolioResponse)
        coEvery { apiService.getPortfolioData() } returns response

        // When & Then
        repository.getPortfolioData().test {
            val loadingItem = awaitItem()
            assertTrue(loadingItem is Resource.Loading)

            val successItem = awaitItem()
            assertTrue(successItem is Resource.Success)
            assertNotNull(successItem.data)
            assertEquals(1, successItem.data!!.holdings.size)

            awaitComplete()
        }
    }

    @Test
    fun `getPortfolioData should emit Loading then Error when API call fails`() = runTest {
        // Given
        val response = Response.error<com.demo.yedunath_demo.data.model.PortfolioResponse>(
            404, 
            okhttp3.ResponseBody.create(null, "Not Found")
        )
        coEvery { apiService.getPortfolioData() } returns response

        // When & Then
        repository.getPortfolioData().test {
            val loadingItem = awaitItem()
            assertTrue(loadingItem is Resource.Loading)

            val errorItem = awaitItem()
            assertTrue(errorItem is Resource.Error)
            assertEquals("Error: 404", errorItem.message)

            awaitComplete()
        }
    }

    @Test
    fun `getPortfolioData should emit Loading then Error when response body is null`() = runTest {
        // Given
        val response = Response.success<com.demo.yedunath_demo.data.model.PortfolioResponse>(null)
        coEvery { apiService.getPortfolioData() } returns response

        // When & Then
        repository.getPortfolioData().test {
            val loadingItem = awaitItem()
            assertTrue(loadingItem is Resource.Loading)

            val errorItem = awaitItem()
            assertTrue(errorItem is Resource.Error)
            assertEquals("Empty response", errorItem.message)

            awaitComplete()
        }
    }

    @Test
    fun `getPortfolioData should emit Loading then Error when exception occurs`() = runTest {
        // Given
        val exception = RuntimeException("Network error")
        coEvery { apiService.getPortfolioData() } throws exception

        // When & Then
        repository.getPortfolioData().test {
            val loadingItem = awaitItem()
            assertTrue(loadingItem is Resource.Loading)

            val errorItem = awaitItem()
            assertTrue(errorItem is Resource.Error)
            assertEquals("Network error", errorItem.message)

            awaitComplete()
        }
    }

    @Test
    fun `getPortfolioData should handle exception with null message`() = runTest {
        // Given
        val exception = RuntimeException(null as String?)
        coEvery { apiService.getPortfolioData() } throws exception

        // When & Then
        repository.getPortfolioData().test {
            val loadingItem = awaitItem()
            assertTrue(loadingItem is Resource.Loading)

            val errorItem = awaitItem()
            assertTrue(errorItem is Resource.Error)
            assertEquals("An unexpected error occurred", errorItem.message)

            awaitComplete()
        }
    }
}
