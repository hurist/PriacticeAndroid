package com.hurist.testapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.hurist.testapplication.base.BaseActivity
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
    }
}