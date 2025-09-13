package com.demo.yedunath_demo.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.yedunath_demo.presentation.adapter.HoldingsAdapter
import com.demo.yedunath_demo.R
import com.demo.yedunath_demo.domain.model.CalculatedPortfolio
import com.demo.yedunath_demo.presentation.viewmodel.PortfolioViewModel
import com.demo.yedunath_demo.utils.Constants
import com.demo.yedunath_demo.utils.Resource
import com.demo.yedunath_demo.utils.UIUtils
import com.demo.yedunath_demo.utils.UIUtils.getCurrencyText
import com.demo.yedunath_demo.utils.UIUtils.getPercentageText
import com.demo.yedunath_demo.utils.UIUtils.setPnlColor
import com.google.android.material.tabs.TabLayout
import android.widget.TextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@AndroidEntryPoint

class PortfolioFragment : Fragment() {
    
    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var holdingsAdapter: HoldingsAdapter
    private lateinit var tvTotalPnl: TextView
    private lateinit var tvTotalPnlPercentage: TextView
    private lateinit var llSummaryHeader: ConstraintLayout
    private lateinit var llExpandableDetails: ConstraintLayout
    private lateinit var ivExpandArrow: ImageView
    private lateinit var tvCurrentValue: TextView
    private lateinit var tvTotalInvestment: TextView
    private lateinit var tvTodaysPnl: TextView
    
    private var isExpanded = false
    
    private val viewModel: PortfolioViewModel by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_portfolio, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initViews(view)
        setupTabs()
        setupRecyclerView()
        setupExpandableView()
        observeViewModel()
    }
    
    private fun initViews(view: View) {
        tabLayout = view.findViewById(R.id.tabLayout)
        recyclerView = view.findViewById(R.id.recyclerView)
        tvTotalPnl = view.findViewById(R.id.tvTotalPnl)
        tvTotalPnlPercentage = view.findViewById(R.id.tvTotalPnlPercentage)
        llSummaryHeader = view.findViewById(R.id.llSummaryHeader)
        llExpandableDetails = view.findViewById(R.id.llExpandableDetails)
        ivExpandArrow = view.findViewById(R.id.ivExpandArrow)
        tvCurrentValue = view.findViewById(R.id.tvCurrentValue)
        tvTotalInvestment = view.findViewById(R.id.tvTotalInvestment)
        tvTodaysPnl = view.findViewById(R.id.tvTodaysPnl)
    }
    
    private fun setupTabs() {
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_positions)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_holdings)))
        
        // Set HOLDINGS tab as selected by default
        tabLayout.getTabAt(1)?.select()
        
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { position ->
                    viewModel.onTabSelected(position)
                }
            }
            
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
    
    private fun setupRecyclerView() {
        holdingsAdapter = HoldingsAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = holdingsAdapter
        }
    }
    
    private fun setupExpandableView() {
        llSummaryHeader.setOnClickListener {
            toggleExpandableView()
        }
    }
    
    private fun toggleExpandableView() {
        if (isExpanded) {
            // Collapse
            llExpandableDetails.visibility = View.GONE
            ivExpandArrow.rotation = 0f
            isExpanded = false
        } else {
            // Expand
            llExpandableDetails.visibility = View.VISIBLE
            ivExpandArrow.rotation = 180f
            isExpanded = true
        }
    }
    
    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.portfolioState.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        Log.d("PortfolioFragment", "Loading portfolio data...")
                    }
                    is Resource.Success -> {
                        Log.d("PortfolioFragment", "Portfolio data loaded: ${resource.data}")
                        resource.data?.let { calculatedPortfolio ->
                            // Update adapter with holdings
                            holdingsAdapter.updateHoldings(calculatedPortfolio.portfolio.holdings)
                            
                            // Update portfolio summary
                            updatePortfolioSummary(calculatedPortfolio)
                        }
                    }
                    is Resource.Error -> {
                        Log.e("PortfolioFragment", "Error loading portfolio: ${resource.message}")
                    }
                }
            }
        }
    }
    
    private fun updatePortfolioSummary(calculatedPortfolio: CalculatedPortfolio) {
        val calculations = calculatedPortfolio.calculations
        
        // Update total P&L display with formatting and colors
        val totalPnlText = getCurrencyText(requireContext(), calculations.totalPnl)
        val totalPnlPercentageText = getPercentageText(requireContext(), calculations.totalPnlPercentage)
        
        tvTotalPnl.text = totalPnlText
        tvTotalPnlPercentage.text = totalPnlPercentageText
        tvTotalPnl.setPnlColor(requireContext(), calculations.totalPnl)
        tvTotalPnlPercentage.setPnlColor(requireContext(), calculations.totalPnl)
        
        // Update expandable details with formatting and colors
        tvCurrentValue.text = getCurrencyText(requireContext(), calculations.totalCurrentValue)
        tvTotalInvestment.text = getCurrencyText(requireContext(), calculations.totalInvestment)
        tvTodaysPnl.text = getCurrencyText(requireContext(), calculations.totalTodaysPnl)
        tvTodaysPnl.setPnlColor(requireContext(), calculations.totalTodaysPnl)
        
        Log.d("PortfolioFragment", "Total Current Value: ${getCurrencyText(requireContext(), calculations.totalCurrentValue)}")
        Log.d("PortfolioFragment", "Total Investment: ${getCurrencyText(requireContext(), calculations.totalInvestment)}")
        Log.d("PortfolioFragment", "Total P&L: $totalPnlText $totalPnlPercentageText")
        Log.d("PortfolioFragment", "Today's P&L: ${getCurrencyText(requireContext(), calculations.totalTodaysPnl)}")
    }
}
