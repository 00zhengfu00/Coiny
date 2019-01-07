package com.binarybricks.coiny.stories

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import android.support.v7.app.AppCompatActivity
import com.binarybricks.coiny.R
import com.binarybricks.coiny.stories.coinsearch.CoinDiscoveryFragment
import com.binarybricks.coiny.stories.dashboard.CoinDashboardFragment
import com.binarybricks.coiny.stories.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun buildLaunchIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }

        const val FRAGMENT_HOME = "FRAGMENT_HOME"
        const val FRAGMENT_OTHER = "FRAGMENT_OTHER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        switchToDashboard(savedInstanceState)

        // if fragment exist reuse it
        // if not then add it


        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.actionHome -> {
                    switchToDashboard(savedInstanceState)
                }

                R.id.actionSearch -> {
                    switchToSearch(savedInstanceState)
                }

                R.id.actionSettings -> {
                    switchToSettings(savedInstanceState)
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                finish()
            } else if (!supportFragmentManager.fragments.isNullOrEmpty()) {
                val fragment = supportFragmentManager.fragments[0]

                if (fragment is CoinDashboardFragment) {
                    bottomNavigation.menu.getItem(0).isChecked = true
                } else if (fragment is CoinDiscoveryFragment) {
                    bottomNavigation.menu.getItem(1).isChecked = true
                } else if (fragment is SettingsFragment) {
                    bottomNavigation.menu.getItem(2).isChecked = true
                }
            }
        }
    }

    private fun switchToDashboard(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val coinDashboardFragment = CoinDashboardFragment()

            // if we switch to home clear everything
            supportFragmentManager.popBackStack(FRAGMENT_OTHER, POP_BACK_STACK_INCLUSIVE)

            supportFragmentManager.beginTransaction()
                    .replace(R.id.containerLayout, coinDashboardFragment, CoinDashboardFragment.TAG)
                    .addToBackStack(FRAGMENT_HOME)
                    .commit()
        }
    }

    private fun switchToSearch(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val coinDiscoveryFragment = CoinDiscoveryFragment()

            supportFragmentManager.beginTransaction()
                    .replace(R.id.containerLayout, coinDiscoveryFragment, CoinDiscoveryFragment.TAG)
                    .addToBackStack(FRAGMENT_OTHER)
                    .commit()
        }
    }

    private fun switchToSettings(savedInstanceState: Bundle?) {

        if (savedInstanceState == null) {
            val settingsFragment = SettingsFragment()

            supportFragmentManager.beginTransaction()
                    .replace(R.id.containerLayout, settingsFragment, SettingsFragment.TAG)
                    .addToBackStack(FRAGMENT_OTHER)
                    .commit()
        }
    }
}
