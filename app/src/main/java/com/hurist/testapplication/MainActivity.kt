package com.hurist.testapplication

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.InputFilter
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hurist.testapplication.base.BaseActivity
import com.hurist.testapplication.extension.startActivity
import com.hurist.testapplication.ui.activity.*
import com.hurist.testapplication.util.*
import com.hurist.testapplication.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

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
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        result.data?.data?.let { it1 -> dumpImageMetaData(it1) }
        result.data?.data?.let { uri ->
            var bitmap: Bitmap? = null
            //解析uri，以流的方式读取文件
            contentResolver.openFileDescriptor(uri, "r")?.use { parcelFileDescriptor ->
                FileInputStream(parcelFileDescriptor.fileDescriptor).use {
                    bitmap = BitmapFactory.decodeStream(it)
                    ivResult.setImageBitmap(bitmap)
                }
            }
            MediaStore.Images.Media.RELATIVE_PATH
        }

        Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show()
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
        btnAnimator.setOnClickListener(this::onClick)

        etInput.filters = arrayOf(InputFilter { source, start, end, dest, dstart, dend ->
            if (source.toString() !in "0".."8") {
                //返回空字符串表示删除本次输入
                return@InputFilter ""
            }
            //返回Null表示接受本次改变
            return@InputFilter null
        })

        lifecycle.addObserver(LifeCycleWatcher(this))
    }

    fun onClick(view: View) {
        when(view.id) {
            btnSetting.id    -> startActivity<SettingActivity>()
            btnBiometric.id  -> startActivity<BiometricActivity>()
            btnWebView.id    -> startActivity<WebViewActivity>()
            btnCustomView.id -> startActivity<CustomViewActivity>()
            btnNetTest.id    -> startActivity<NetTestActivity>()
            btnNotify.id     -> startActivity<NotificationActivity>()
            btnToolbar.id    -> startActivity<ToolbarActivity>()
            btnAnimator.id   -> startActivity<AnimatorActivity>()
            btnService.id    -> startActivity<ServiceActivity>()
            btnFile.id       -> startActivity<FileActivity>()
            btnList.id       -> startActivity<MainListActivity>()
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
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.type = "image/*"
                launcher.launch(intent)
            }
            btnShare.id      -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
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

    private fun dumpImageMetaData(uri: Uri) {

        // The query, because it only applies to a single document, returns only
        // one row. There's no need to filter, sort, or select fields,
        // because we want all fields for one document.
        val cursor: Cursor? = contentResolver.query(
            uri, null, null, null, null, null)

        cursor?.use {
            // moveToFirst() returns false if the cursor has 0 rows. Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (it.moveToFirst()) {

                // Note it's called "Display Name". This is
                // provider-specific, and might not necessarily be the file name.
                val displayName: String =
                    it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                Log.i(TAG, "Display Name: $displayName")

                val sizeIndex: Int = it.getColumnIndex(OpenableColumns.SIZE)
                // If the size is unknown, the value stored is null. But because an
                // int can't be null, the behavior is implementation-specific,
                // and unpredictable. So as
                // a rule, check if it's null before assigning to an int. This will
                // happen often: The storage API allows for remote files, whose
                // size might not be locally known.
                val size: String = if (!it.isNull(sizeIndex)) {
                    // Technically the column stores an int, but cursor.getString()
                    // will do the conversion automatically.
                    it.getString(sizeIndex)
                } else {
                    "Unknown"
                }
                Log.i(TAG, "Size: $size")
            }
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            context.startActivity(starter)
        }
    }
}
