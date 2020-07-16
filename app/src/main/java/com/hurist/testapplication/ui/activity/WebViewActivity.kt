package com.hurist.testapplication.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hurist.testapplication.R
import com.hurist.testapplication.util.WebClient
import kotlinx.android.synthetic.main.activity_web_view.*


class WebViewActivity : AppCompatActivity() {

    private val webClient by lazy {
        WebClient() { requestCode ->
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.addCategory(Intent.CATEGORY_OPENABLE)
            i.type = "*/*"
            startActivityForResult(i, requestCode)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)


        webView.loadUrl("https://www.ll-kefu.com/chat/chatClient/chatbox.jsp?companyID=8999&&configID=110&skillId=60")
        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = webClient
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.let { webClient.receive(requestCode, resultCode, it) }
    }
}