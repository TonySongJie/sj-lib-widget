package com.xuan.widget.web

import android.webkit.WebChromeClient
import android.webkit.ValueCallback
import android.app.Activity
import android.net.Uri
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.text.format.DateFormat
import android.webkit.WebView
import java.io.File
import java.util.*

class H5WebChromeClient(
    private var mActivity: Activity?
) : WebChromeClient() /*{

    private val CHOOSE_REQUEST_CODE = 0x9001

    private var uploadFile: ValueCallback<Uri>? = null//定义接受返回值
    private var uploadFiles: ValueCallback<Array<Uri>>? = null
    private var mInputFileCallback: InputFileCallback? = null

    // For Android 3.0+
    fun openFileChooser(uploadMsg: ValueCallback<Uri>, acceptType: String) {
        this.uploadFile = uploadFile
        openFileChooseProcess()
    }

    // For Android < 3.0
    fun openFileChooser(uploadMsgs: ValueCallback<Uri>) {
        this.uploadFile = uploadFile
        openFileChooseProcess()
    }

    // For Android  > 4.1.1
    fun openFileChooser(uploadMsg: ValueCallback<Uri>, acceptType: String, capture: String) {
        this.uploadFile = uploadFile
        openFileChooseProcess()
    }

    // For Android  >= 5.0
    override fun onShowFileChooser(
        webView: WebView,
        filePathCallback: ValueCallback<Array<Uri>>,
        fileChooserParams: FileChooserParams
    ): Boolean {
        this.uploadFiles = filePathCallback
        openFileChooseProcess()
        return true
    }

    private fun openFileChooseProcess() {
//        val i = Intent(Intent.ACTION_GET_CONTENT)
//        i.addCategory(Intent.CATEGORY_OPENABLE)
//        i.type = "image/*"
//        mActivity?.startActivityForResult(Intent.createChooser(i, "Choose"), CHOOSE_REQUEST_CODE)

        val filePath = (Environment.getExternalStorageDirectory().toString() + File.separator
                + Environment.DIRECTORY_PICTURES + File.separator)
        val fileName = "IMG_" + DateFormat.format(
            "yyyyMMdd_hhmmss",
            Calendar.getInstance(Locale.CHINA)
        ) + ".jpg"

        val imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val fileProvider = "${AppUtils.getAppPackageName()}.fileProvider"
            FileProvider.getUriForFile(mActivity!!, fileProvider, File("$filePath$fileName"))
        } else {
            Uri.fromFile(File(filePath + fileName))
        }

        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

        val photo = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        val chooserIntent = Intent.createChooser(photo, "拍照/选择图片")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf<Parcelable>(captureIntent))

        if (mInputFileCallback != null) {
            mInputFileCallback!!.onForResult(chooserIntent, "$filePath$fileName")
        }
    }

    fun addInputFileCallback(callback: InputFileCallback) {
        this.mInputFileCallback = callback
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        EFLogger.d("requestCode===", "$requestCode====")
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CHOOSE_REQUEST_CODE -> {
                    if (null != uploadFile) {
                        val result = if (data == null || resultCode != Activity.RESULT_OK)
                            null
                        else
                            data.data
                        uploadFile?.onReceiveValue(result)
                        uploadFile = null
                    }
                    if (null != uploadFiles) {
                        val result = if (data == null || resultCode != Activity.RESULT_OK)
                            null
                        else
                            data.data
                        uploadFiles?.onReceiveValue(arrayOf(result!!))
                        uploadFiles = null
                    }
                }
                else -> {
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            if (null != uploadFile) {
                uploadFile?.onReceiveValue(null)
                uploadFile = null
            }
            if (null != uploadFiles) {
                uploadFiles?.onReceiveValue(null)
                uploadFiles = null
            }
        }
    }
}*/