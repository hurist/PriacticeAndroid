package com.hurist.testapplication.ui.activity

import android.content.Context
import android.net.*
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.hurist.testapplication.R
import com.hurist.testapplication.TestApplication
import com.hurist.testapplication.base.BaseActivity
import com.hurist.testapplication.util.NetWorkUtils
import com.hurist.testapplication.util.toast
import com.hurist.testapplication.viewmodel.NetTestViewModel
import kotlinx.android.synthetic.main.activity_net_test.*

class NetTestActivity : BaseActivity() {

    private val viewModel by lazy { ViewModelProvider(this)[NetTestViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net_test)

        btnLogin.setOnClickListener {
            viewModel.login()
        }

        viewModel.userInfo.observe(this) {
            tvResult.text = "$it \n ${it.body()}"
        }

        val manager = TestApplication.appContext.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val TAG = "NetworkCallback"
        manager.registerNetworkCallback(NetworkRequest.Builder().build(), object: ConnectivityManager.NetworkCallback() {
            override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
                super.onBlockedStatusChanged(network, blocked)
                Log.d(TAG, "onBlockedStatusChanged: ")
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                Log.d(TAG, "onCapabilitiesChanged: ")
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                Log.d(TAG, "onLost: ")
            }

            override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
                super.onLinkPropertiesChanged(network, linkProperties)
                Log.d(TAG, "onLinkPropertiesChanged: ")
            }

            override fun onUnavailable() {
                super.onUnavailable()
                Log.d(TAG, "onUnavailable: ")
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                super.onLosing(network, maxMsToLive)
                Log.d(TAG, "onLosing: ")
            }

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                Log.d(TAG, "onAvailable: ")
            }
        })
    }
}