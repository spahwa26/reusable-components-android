package com.nickelfox.media_picker.ui

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.nickelfox.media_picker.R
import com.nickelfox.media_picker.utils.PermissionUtils
import java.io.File

class MediaPickerForActivity(private val activity: Activity) {
    private var onMediaPickedListener: ((List<Uri>, List<String>) -> Unit)? = null
    private var isMultiple: Boolean = false
    private var isVideo: Boolean = false
    private var isBothImagesVideos: Boolean = false
    private var showLongPressInstructDialog = true
    private var isCropped: Boolean = false
    private var isOval: Boolean = false

    private var onCroppedImageListener: ((Bitmap, File) -> Unit)? = null

    private var permissionLauncher =
        (activity as AppCompatActivity).registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionList ->
            val allGranted = permissionList.all { it.value }
            if (allGranted)
                startMediaPicker(isMultiple, isVideo, isBothImagesVideos)
        }

    private var selectImage =
        (activity as AppCompatActivity).registerForActivityResult(
            SelectMediaContract(
                activity,
                isVideo
            )
        ) {
            it?.first?.let { it1 ->
                if (isCropped) {
                    continueCroppingImage(it1[0], isOval)
                    isCropped = false
                    isOval = false
                } else {
                    it.second?.let { it2 ->
                        onMediaPickedListener?.invoke(
                            it1,
                            it2
                        )
                    }
                }
            }
        }

    fun pickMedia(
        isMultiple: Boolean,
        isVideoOnly: Boolean,
        isBothImagesVideos: Boolean = false,
        listener: (List<Uri>, List<String>) -> Unit
    ) {
        this.onMediaPickedListener = listener
        this.isMultiple = isMultiple
        this.isVideo = isVideoOnly
        this.isBothImagesVideos = isBothImagesVideos
        if (PermissionUtils.isPermissionsGranted(activity, isVideo, isBothImagesVideos)) {
            startMediaPicker(isMultiple, isVideoOnly, isBothImagesVideos)
        } else {
            PermissionUtils.requestPermissions(
                activity,
                isVideo,
                isBothImagesVideos
            ) { granted, list ->
                if (granted)
                    startMediaPicker(isMultiple, isVideoOnly, isBothImagesVideos)
                else
                    permissionLauncher.launch(list?.toTypedArray())
            }
        }
    }

    private fun continueCroppingImage(uri: Uri, isOval: Boolean) {
        ImageCropFragment
            .newInstance(uri = uri, if (isOval) ImageShape.OVAL else ImageShape.RECTANGLE)
            .setOnCropSuccessListener(onCroppedImageListener)
            .show((activity as AppCompatActivity).supportFragmentManager, null)
    }

    private fun startMediaPicker(
        isMultiple: Boolean,
        isVideoOnly: Boolean,
        isBothImagesVideos: Boolean
    ) {
        val pickType = when {
            isBothImagesVideos -> listOf("image/*", "video/*")
            isVideoOnly -> listOf("video/*")
            else -> listOf("image/*")
        }
        if (isMultiple && showLongPressInstructDialog)
            showAlertDialog(pickType)
        else
            selectImage.launch(Pair(pickType.toTypedArray(), isMultiple))
    }

    private fun showAlertDialog(pickType: List<String>) {
        val builder = AlertDialog.Builder(activity)
            .setTitle(activity.getString(R.string.multiple_selection))
            .setMessage(activity.getString(R.string.long_press))
            .setPositiveButton(
                activity.getString(R.string.ok)
            ) { _, _ -> selectImage.launch(Pair(pickType.toTypedArray(), true)) }
        builder.create().show()
        showLongPressInstructDialog = false
    }
}