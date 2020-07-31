package com.hurist.testapplication.ui.activity

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.hurist.testapplication.R
import com.hurist.testapplication.base.BaseActivity
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_file.*
import permissions.dispatcher.*
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream


private const val FILE_NAME = "DATA"

@RuntimePermissions
class FileActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btnWrite -> {
                openFileOutput(FILE_NAME, Context.MODE_PRIVATE).bufferedWriter().use {
                    it.write(etInput.text.toString())
                }
            }
            R.id.btnRead -> {
                openFileInput(FILE_NAME).bufferedReader().use {
                    tvResult.text = it.readText()
                }
            }
            R.id.btnNewCache -> {
                File.createTempFile("Cache", null, cacheDir)
                cacheDir.listFiles()?.forEach {
                    Timber.d(it.name)
                }
            }
            R.id.btnDeleteCache -> {
                cacheDir.deleteRecursively()
                cacheDir.listFiles()?.forEach {
                    Timber.d(it.name)
                }
            }
            R.id.btnQueryState -> {
                Timber.d("外部存储空间状态：" + Environment.getExternalStorageState())
                getExternalStorageDirectory()
            }
            R.id.btnExternalFileCI -> {
                createAndInsertFileWithPermissionCheck(
                    File(
                        getExternalFilesDir("MediaPhoto"),
                        "ExternalFile.txt"
                    )
                )
            }
            R.id.btnExternalCacheFileCI -> {
                createAndInsertFileWithPermissionCheck(
                    File(
                        externalCacheDir,
                        "ExternalCacheFile.txt"
                    )
                )
            }
            R.id.btnExternalFileDelete -> {
                File(getExternalFilesDir("MediaPhoto"), "ExternalFile.txt").delete()
            }
            R.id.btnExternalCacheFileDelete -> {
                File(externalCacheDir, "ExternalCacheFile.txt").delete()
            }
            R.id.btnExternalMediaSave -> {
                saveToMediaWithPermissionCheck()
            }
        }
    }

    /**
     * 存储到外置存储应用专属的媒体文件夹下，其实只是用[Environment.DIRECTORY_DOCUMENTS]来规范路径，这个目录不是
     * 公共的
     */
    @NeedsPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun saveToMedia() {
        val file = File(
            getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS
            ), "testDocument.text"
        )
        file.createNewFile()
    }

    @NeedsPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun getExternalStorageDirectory() {
        //这个是外部存储的根目录
        Logger.d(Environment.getExternalStorageDirectory().absolutePath)
        //这个是外部存储空间中的应用专用目录，这里的null实际是个类型参数，传入一个不为null的字符串会创建一个同名的子文件夹
        Logger.d(ContextCompat.getExternalFilesDirs(this, null))
        Logger.d(getExternalFilesDir(null)?.absolutePath)
        //缓存目录
        Logger.d(externalCacheDir?.absolutePath)
    }

    @NeedsPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun createAndInsertFile(file: File) {
        if (file.exists()) {
            //读文件文件必须存在
            file.inputStream().bufferedReader().use {
                it.forEachLine { lines ->
                    println(lines)
                }
            }
        } else {
            Logger.d("文件不存在")
        }
        //对文件进行写的时候如果文件不存在会自动创建
        //这种方法会导致文件被重写
        file.bufferedWriter().use { writer ->
            repeat(100) {
                writer.appendln("New Insert $it")
            }
        }
        //这种方式可以对现有文件进行追加
        //或者这个也行test.appendText()
        FileOutputStream(file, true).bufferedWriter().use { writer ->
            repeat(20) {
                writer.appendln("Append $it")
            }
        }
    }

    @OnShowRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun showRationaleForCamera(request: PermissionRequest) {
        showRationaleDialog("需要读取外置存储空间", request)
    }

    @OnPermissionDenied(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onCameraDenied() {
        Toast.makeText(this, "读取文件权限被拒绝", Toast.LENGTH_SHORT).show()
    }

    @OnNeverAskAgain(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onCameraNeverAskAgain() {
        Toast.makeText(this, "读取文件权限被拒绝且不再显示", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated function
        onRequestPermissionsResult(requestCode, grantResults)
    }

    private fun showRationaleDialog(message: String, request: PermissionRequest) {
        AlertDialog.Builder(this)
            .setPositiveButton("同意") { _, _ -> request.proceed() }
            .setNegativeButton("拒绝") { _, _ -> request.cancel() }
            .setCancelable(false)
            .setMessage(message)
            .show()
    }
}