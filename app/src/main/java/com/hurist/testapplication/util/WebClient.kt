package com.hurist.testapplication.util

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.webkit.*

/**
 * author: spike
 * version：1.0
 * create data：2020/7/6
 * Description：WebChromeClient
 */
class WebClient(val openFileChooser: (Int)->Unit) : WebChromeClient() {

    private var uploadFile: ValueCallback<Uri>? = null
    private var uploadFiles: ValueCallback<Array<Uri>>? = null
    private val WEB_CLIENT_CODE = 1000001

    /**
     * For Android < 3.0
     */
    fun openFileChooser(valueCallback: ValueCallback<Uri>): Unit {
        openFileChooser(WEB_CLIENT_CODE)
        uploadFile = valueCallback
    }

    /**
     * For Android 3.0+
     */
    fun openFileChooser(valueCallback: ValueCallback<Uri>, acceptType: String): Unit {
        openFileChooser(WEB_CLIENT_CODE)
        uploadFile = valueCallback
    }

    /**
     * For Android > 4.1.1
     */
    fun openFileChooser(
        valueCallback: ValueCallback<Uri>,
        acceptType: String,
        capture: String
    ): Unit {
        openFileChooser(WEB_CLIENT_CODE)
        uploadFile = valueCallback
    }

    /**
     * For Android >= 5.0
     */
    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {
        openFileChooser(WEB_CLIENT_CODE)
        uploadFiles = filePathCallback
        return true
    }

    fun receive(requestCode: Int, resultCode: Int, result: Intent) {
        if (requestCode == WEB_CLIENT_CODE) {
            if (resultCode == RESULT_OK) {
                uploadFile?.onReceiveValue(result.data)
                uploadFile = null
                uploadFiles?.onReceiveValue(arrayOf(result.data!!))
                uploadFiles = null
            } else {
                uploadFile?.onReceiveValue(null)
                uploadFile = null
                uploadFiles?.onReceiveValue(null)
                uploadFiles = null
            }
        }

    }

    interface CallBack {
        fun openFileChooser(requestCode: Int)
    }
}