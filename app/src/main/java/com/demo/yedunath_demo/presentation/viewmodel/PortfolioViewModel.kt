package com.demo.yedunath_demo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.yedunath_demo.domain.model.CalculatedPortfolio
import com.demo.yedunath_demo.domain.usecase.GetPortfolioDataUseCase
import com.demo.yedunath_demo.domain.usecase.CalculatePortfolioUseCase
import com.demo.yedunath_demo.utils.Constants
import com.demo.yedunath_demo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val getPortfolioDataUseCase: GetPortfolioDataUseCase,
    private val calculatePortfolioUseCase: CalculatePortfolioUseCase
) : ViewModel() {

    private val _portfolioState = MutableStateFlow<Resource<CalculatedPortfolio>>(Resource.Loading())
    val portfolioState: StateFlow<Resource<CalculatedPortfolio>> = _portfolioState.asStateFlow()

    private val _selectedTab = MutableStateFlow(1) // 0 = Positions, 1 = Holdings
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    init {
        loadHoldingsData()
    }

    fun loadHoldingsData() {
        viewModelScope.launch {
            getPortfolioDataUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _portfolioState.value = Resource.Loading()
                    }
                    is Resource.Success -> {
                        result.data?.let { portfolio ->
                            val calculatedPortfolio = calculatePortfolioUseCase(portfolio)
                            _portfolioState.value = Resource.Success(calculatedPortfolio)
                        } ?: run {
                            _portfolioState.value = Resource.Error("Empty portfolio data")
                        }
                    }
                    is Resource.Error -> {
                        _portfolioState.value = Resource.Error(result.message ?: "Unknown error")
                    }
                }
            }
        }
    }

    fun onTabSelected(position: Int) {
        _selectedTab.value = position
        when (position) {
            0 -> {} // Positions tab - no implementation needed
            1 -> loadHoldingsData()
        }
    }
}
