package com.bobabelga.moviesapp.ui

import android.net.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bobabelga.moviesapp.R
import com.bobabelga.moviesapp.adapters.ViewPagerAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val TAG = "Boba"

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var relativeLayout: RelativeLayout
    var viewPagerIsCreated = false

    private val listTitles = listOf("Popular Movies", "Favorite Movies", "Search Movies")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager = findViewById(R.id.pager)
        tabLayout = findViewById(R.id.tab_layout)


        relativeLayout = findViewById(R.id.linearLayout2)


        val connectivityManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getSystemService(ConnectivityManager::class.java)
        } else {
            TODO("VERSION.SDK_INT < M")
        }
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities == null) {
            relativeLayout.visibility = View.VISIBLE
        } else {
            viewPager.adapter = viewPagerAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = listTitles[position]
            }.attach()
            viewPagerIsCreated = true
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(object :
                ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    GlobalScope.launch(Dispatchers.Main) {
                        relativeLayout.visibility = View.INVISIBLE
                        if (!viewPagerIsCreated){
                            viewPager.adapter = viewPagerAdapter
                            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                                tab.text = listTitles[position]
                            }.attach()
                        }
                    }
                    Log.e(TAG, "The default network is now: " + network)
                }

                override fun onLost(network: Network) {
                    GlobalScope.launch(Dispatchers.Main) {
                        relativeLayout.visibility = View.VISIBLE
                    }
                    Log.e(
                        TAG,
                        "The application no longer has a default network. The last default network was " + network
                    )
                }

                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    Log.e(TAG, "The default network changed capabilities: " + networkCapabilities)
                }

                override fun onLinkPropertiesChanged(
                    network: Network,
                    linkProperties: LinkProperties
                ) {
                    Log.e(TAG, "The default network changed link properties: " + linkProperties)
                }
            })
        }

    }

}