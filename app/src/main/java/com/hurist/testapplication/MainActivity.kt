package com.hurist.testapplication

import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.hurist.testapplication.base.BaseActivity
import com.hurist.testapplication.extension.startActivity
import com.hurist.testapplication.util.*
import com.hurist.testapplication.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * author: spike
 * version：1.0
 * create data：2020/6/28 10:33
 * Description：
 */
class MainActivity : BaseActivity(R.layout.activity_main) {

    private val viewModel by lazy { ViewModelProvider(this)[MainActivityViewModel::class.java] }
    private val popupWin by lazy {
        getAPopupWindow(this, R.layout.popup_test)
    }
    private val dialog by lazy {
        AlertDialog.Builder(this).run {
            setTitle("这是标题")
            setMessage("这是提醒的内容")
            create()
        }
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
    }
    private var actionMode: ActionMode? = null
    private val actionCallback = object: ActionMode.Callback2() {
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return false;
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.menu_toolbar_activity, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            actionMode = null
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.data.observe(this, Observer {
            Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).apply {
                setGravity(Gravity.CENTER, 0, 0)
                show()
            }
            result.text = it.toString()
        })

        btnBiometric.setOnClickListener(this::onClick)
        btnWebView.setOnClickListener(this::onClick)
        btnCustomView.setOnClickListener(this::onClick)
        btnImage.setOnClickListener(this::onClick)
        btnPopup.setOnClickListener(this::onClick)
        btnDialog.setOnClickListener(this::onClick)
        btnSetting.setOnClickListener(this::onClick)
        add.setOnClickListener(this::onClick)
        btnNetTest.setOnClickListener(this::onClick)
        btnNotify.setOnClickListener(this::onClick)
        btnToolbar.setOnClickListener(this::onClick)
        btnPopupMenu.setOnClickListener(this::onClick)
        btnActionMode.setOnClickListener(this::onClick)
        etNumber.filters = arrayOf(EditTextPointLengthFilter(4))

        lifecycle.addObserver(LifeCycleWatcher(this))
    }

    private fun onClick(view: View) {
        when(view.id) {
            btnSetting.id    -> startActivity<SettingActivity>()
            btnBiometric.id  -> startActivity<BiometricActivity>()
            btnWebView.id    -> startActivity<WebViewActivity>()
            btnCustomView.id -> startActivity<CustomViewActivity>()
            btnNetTest.id    -> startActivity<NetTestActivity>()
            btnNotify.id     -> startActivity<NotificationActivity>()
            btnToolbar.id    -> startActivity<ToolbarActivity>()
            btnPopup.id      -> popupWin.showAtLocation(view, Gravity.CENTER, 0, 0)
            btnDialog.id     -> dialog.show()
            add.id           -> viewModel.addNew()
            btnPopupMenu.id  -> showPopupMenu(view)
            btnActionMode.id -> {
                if (actionMode == null) {
                    actionMode = startActionMode(actionCallback)
                }
            }
            btnImage.id      -> {
                //判断权限
                val intent = Intent("android.intent.action.GET_CONTENT")
                intent.type = "image/*"
                launcher.launch(intent)
            }
        }
    }

    private fun showPopupMenu(v: View) {
        PopupMenu(this, v).apply {
            inflate(R.menu.menu_pop_menu)
            setOnMenuItemClickListener {
                return@setOnMenuItemClickListener false
            }
            show()
        }
    }

    override fun onDestroy() {
        if (isFinishing) {
            Log.d(TAG, "onDestroy: 这是要销毁了呀")
            Toast.makeText(this, "这是要销毁了呀", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "这是要重建了呀", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "onDestroy: 这是要重建了呀")
        }
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            context.startActivity(starter)
        }
    }
}
