package com.hurist.testapplication.util

import android.content.Context
import android.net.*
import android.net.wifi.WifiManager
import android.widget.Toast
import com.hurist.testapplication.TestApplication
import java.net.*
import java.util.*


/**
 * author: spike
 * version：1.0
 * create data：2020/8/7
 * Description：NetUtils
 */
object NetWorkUtils {
    /**
     * 检查网络是否可用
     *
     * @param paramContext
     * @return
     */
    fun checkEnable(paramContext: Context): Boolean {
        val i = false
        val localNetworkInfo = (paramContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return localNetworkInfo != null && localNetworkInfo.isAvailable
    }

    fun isOnline() {
        val manager = TestApplication.appContext.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        manager.allNetworkInfo.forEach { network ->
            network
        }

        manager.registerNetworkCallback(NetworkRequest.Builder().build(), object: ConnectivityManager.NetworkCallback() {
            override fun onUnavailable() {
                super.onUnavailable()
            }

            override fun onAvailable(network: Network) {
                super.onAvailable(network)

            }
        })

    }

    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    fun int2ip(ipInt: Int): String {
        val sb = StringBuilder()
        sb.append(ipInt and 0xFF).append(".")
        sb.append(ipInt shr 8 and 0xFF).append(".")
        sb.append(ipInt shr 16 and 0xFF).append(".")
        sb.append(ipInt shr 24 and 0xFF)
        return sb.toString()
    }

    /**
     * 获取当前ip地址
     *
     * @param context
     * @return
     */
    fun getLocalIpAddress(context: Context): String {
        return try {
            val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiManager.connectionInfo
            val i = wifiInfo.ipAddress
            int2ip(i)
        } catch (ex: Exception) {
            Toast.makeText(context, ex.message, Toast.LENGTH_SHORT).show()
            "获取IP出错鸟!!!!请保证是WIFI,或者请重新打开网络!${ex.message}"
        }

    }

    //GPRS连接下的ip
    fun getLocalIpAddress(): String? {
        try {
            val en: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf: NetworkInterface = en.nextElement()
                val enumIpAddr: Enumeration<InetAddress> = intf.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress: InetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress) {
                        return inetAddress.hostAddress.toString()
                    }
                }
            }
        } catch (ex: SocketException) {
            "WifiPreference IpAddress ${ex.message}"
        }
        return null
    }
}