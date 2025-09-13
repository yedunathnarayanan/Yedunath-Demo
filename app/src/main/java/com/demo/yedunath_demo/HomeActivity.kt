package com.demo.yedunath_demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.demo.yedunath_demo.fragments.OrdersFragment
import com.demo.yedunath_demo.fragments.PortfolioFragment
import com.demo.yedunath_demo.fragments.WatchlistFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class HomeActivity : AppCompatActivity() {
    
    private lateinit var bottomNavigation: BottomNavigationView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Set window flags to handle status bar properly
        window.statusBarColor = getColor(R.color.blue_header)
        
        setContentView(R.layout.activity_home)
        
        supportActionBar?.hide()
        
        initViews()
        setupBottomNavigation()
        
        // Load default fragment (Portfolio)
        if (savedInstanceState == null) {
            loadFragment(PortfolioFragment())
        }
    }
    
    private fun initViews() {
        bottomNavigation = findViewById(R.id.bottomNavigation)
    }
    
    private fun setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_portfolio -> {
                    loadFragment(PortfolioFragment())
                    true
                }
                R.id.nav_watchlist -> {
                    loadFragment(WatchlistFragment())
                    true
                }
                R.id.nav_orders -> {
                    loadFragment(OrdersFragment())
                    true
                }
                else -> false
            }
        }
        
        // Set Portfolio as selected by default
        bottomNavigation.selectedItemId = R.id.nav_portfolio
    }
    
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
