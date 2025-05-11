package com.example.smo1.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.smo1.*
import com.google.android.material.navigation.NavigationView

class ViewOrderStatusActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_order_status)

        // Setup Toolbar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Setup Drawer
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        // Enable toolbar button to open drawer
        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Handle Navigation Clicks
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.overall_dashboard -> replaceFragment(OverallDashboardFragment(), "Overall Dashboard")
                R.id.worker_dashboard -> replaceFragment(WorkerDashboardFragment(), "Worker Dashboard")
                R.id.machine_dashboard -> replaceFragment(MachineDashboardFragment(), "Machine Dashboard")
                R.id.job_dashboard -> replaceFragment(JobDashboardFragment(), "Job Dashboard")
                R.id.back_button -> {
                    onBackPressedDispatcher.onBackPressed() // Navigates back without restarting
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START) // Close drawer after selection
            true
        }

        // Load Default Fragment (Overall Dashboard)
        if (savedInstanceState == null) {
            replaceFragment(OverallDashboardFragment(), "Overall Dashboard")
        }
    }

    // ✅ Modify replaceFragment to Update Toolbar Title
    private fun replaceFragment(fragment: Fragment, title: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
        supportActionBar?.title = title // Update Toolbar Title
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
