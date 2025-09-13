package com.demo.yedunath_demo.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.demo.yedunath_demo.TestDataFactory
import com.demo.yedunath_demo.domain.usecase.CalculatePortfolioUseCase
import com.demo.yedunath_demo.domain.usecase.GetPortfolioDataUseCase
import com.demo.yedunath_demo.utils.Resource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PortfolioViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val getPortfolioDataUseCase = mockk<GetPortfolioDataUseCase>()
    private val calculatePortfolioUseCase = mockk<CalculatePortfolioUseCase>()
    
    private lateinit var viewModel: PortfolioViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should load holdings data and emit calculated portfolio`() = runTest {
        // Given
        val portfolioDomain = TestDataFactory.createPortfolioDomain()
        val calculatedPortfolio = mockk<com.demo.yedunath_demo.domain.model.CalculatedPortfolio>()
        
        every { getPortfolioDataUseCase() } returns flowOf(Resource.Success(portfolioDomain))
        every { calculatePortfolioUseCase(portfolioDomain) } returns calculatedPortfolio

        // When
        viewModel = PortfolioViewModel(getPortfolioDataUseCase, calculatePortfolioUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.portfolioState.test {
            val item = awaitItem()
            assertTrue(item is Resource.Success)
            assertEquals(calculatedPortfolio, item.data)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `init should emit error when portfolio data loading fails`() = runTest {
        // Given
        val errorMessage = "Network error"
        every { getPortfolioDataUseCase() } returns flowOf(Resource.Error(errorMessage))

        // When
        viewModel = PortfolioViewModel(getPortfolioDataUseCase, calculatePortfolioUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.portfolioState.test {
            val item = awaitItem()
            assertTrue(item is Resource.Error)
            assertEquals(errorMessage, item.message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `init should emit error when portfolio data is null`() = runTest {
        // Given
        every { getPortfolioDataUseCase() } returns flowOf(Resource.Success(null) as Resource<com.demo.yedunath_demo.domain.model.PortfolioDomain>)

        // When
        viewModel = PortfolioViewModel(getPortfolioDataUseCase, calculatePortfolioUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.portfolioState.test {
            val item = awaitItem()
            assertTrue(item is Resource.Error)
            assertEquals("Empty portfolio data", item.message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `init should emit loading state initially`() = runTest {
        // Given
        every { getPortfolioDataUseCase() } returns flowOf(Resource.Loading())

        // When
        viewModel = PortfolioViewModel(getPortfolioDataUseCase, calculatePortfolioUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.portfolioState.test {
            val item = awaitItem()
            assertTrue(item is Resource.Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadHoldingsData should trigger portfolio data loading`() = runTest {
        // Given
        val portfolioDomain = TestDataFactory.createPortfolioDomain()
        val calculatedPortfolio = mockk<com.demo.yedunath_demo.domain.model.CalculatedPortfolio>()
        
        every { getPortfolioDataUseCase() } returns flowOf(Resource.Success(portfolioDomain))
        every { calculatePortfolioUseCase(portfolioDomain) } returns calculatedPortfolio

        viewModel = PortfolioViewModel(getPortfolioDataUseCase, calculatePortfolioUseCase)

        // When
        viewModel.loadHoldingsData()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify(exactly = 2) { getPortfolioDataUseCase() } // Once in init, once in loadHoldingsData
    }

    @Test
    fun `onTabSelected should update selected tab`() = runTest {
        // Given
        every { getPortfolioDataUseCase() } returns flowOf(Resource.Loading())
        viewModel = PortfolioViewModel(getPortfolioDataUseCase, calculatePortfolioUseCase)

        // When
        viewModel.onTabSelected(0)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.selectedTab.test {
            val item = awaitItem()
            assertEquals(0, item)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onTabSelected with holdings tab should load holdings data`() = runTest {
        // Given
        val portfolioDomain = TestDataFactory.createPortfolioDomain()
        val calculatedPortfolio = mockk<com.demo.yedunath_demo.domain.model.CalculatedPortfolio>()
        
        every { getPortfolioDataUseCase() } returns flowOf(Resource.Success(portfolioDomain))
        every { calculatePortfolioUseCase(portfolioDomain) } returns calculatedPortfolio

        viewModel = PortfolioViewModel(getPortfolioDataUseCase, calculatePortfolioUseCase)

        // When
        viewModel.onTabSelected(1) // Holdings tab
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify(exactly = 2) { getPortfolioDataUseCase() } // Once in init, once in onTabSelected
    }

    @Test
    fun `selectedTab should have default value of 1`() = runTest {
        // Given
        every { getPortfolioDataUseCase() } returns flowOf(Resource.Loading())

        // When
        viewModel = PortfolioViewModel(getPortfolioDataUseCase, calculatePortfolioUseCase)

        // Then
        viewModel.selectedTab.test {
            val item = awaitItem()
            assertEquals(1, item) // Default to Holdings tab
            cancelAndIgnoreRemainingEvents()
        }
    }
}
