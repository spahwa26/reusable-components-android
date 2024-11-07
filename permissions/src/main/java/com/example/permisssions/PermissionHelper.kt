package com.example.permisssions

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

object PermissionHelper {

    enum class PermissionType(val permissionName: String) {
        STORAGE("Storage"),
        CAMERA("Camera"),
        NOTIFICATION("Notification"),
        LOCATION("Location"),
        MICROPHONE("Microphone"),
        CONTACTS("Contacts"),
        CALENDAR("Calendar"),
        PHONE("Phone")
    }

    data class PermissionConfig(
        val requireStorage: Boolean = false,
        val requireCamera: Boolean = false,
        val requireNotification: Boolean = false,
        val requireLocation: Boolean = false,
        val requireMicrophone: Boolean = false,
        val requireContacts: Boolean = false,
        val requireCalendar: Boolean = false,
        val requirePhone: Boolean = false,
        val requestImages: Boolean = false,
        val requestVideos: Boolean = false,
        val requestAudio: Boolean = false,
        val manageExternalStorage: Boolean = false
    )

    private fun Context.isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }
    fun Context.showPermissionDialog(title: String, message: String, onConfirm: () -> Unit) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Go to Settings") { dialog, _ ->
                onConfirm()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
    private fun Context.getStoragePermissions(config: PermissionConfig): List<String> {
        val permissions = mutableListOf<String>()

        if (config.manageExternalStorage && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                showPermissionDialog(
                    title = "Storage Permission Needed",
                    message = "This app requires access to manage external storage. Please grant permission in the settings.",
                    onConfirm = {
                        val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                        startActivity(intent)
                    }
                )
            }
        }

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                if (config.requestImages) permissions.add(Manifest.permission.READ_MEDIA_IMAGES)
                if (config.requestVideos) permissions.add(Manifest.permission.READ_MEDIA_VIDEO)
                if (config.requestAudio) permissions.add(Manifest.permission.READ_MEDIA_AUDIO)
            }
            else -> {
                if (!isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
                if (!isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                    Build.VERSION.SDK_INT < Build.VERSION_CODES.R
                ) {
                    permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }
        }

        return permissions
    }

    private fun Context.getCameraPermissions(config: PermissionConfig): List<String> {
        return if (config.requireCamera && !isPermissionGranted(Manifest.permission.CAMERA)) {
            listOf(Manifest.permission.CAMERA)
        } else emptyList()
    }

    private fun Context.getNotificationPermissions(config: PermissionConfig): List<String> {
        return if (config.requireNotification &&
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            !isPermissionGranted(Manifest.permission.POST_NOTIFICATIONS)
        ) {
            listOf(Manifest.permission.POST_NOTIFICATIONS)
        } else emptyList()
    }

    private fun Context.getLocationPermissions(config: PermissionConfig): List<String> {
        val permissions = mutableListOf<String>()
        if (config.requireLocation) {
            if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            if (!isPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }
        return permissions
    }

    private fun Context.getMicrophonePermissions(config: PermissionConfig): List<String> {
        return if (config.requireMicrophone && !isPermissionGranted(Manifest.permission.RECORD_AUDIO)) {
            listOf(Manifest.permission.RECORD_AUDIO)
        } else emptyList()
    }

    private fun Context.getContactsPermissions(config: PermissionConfig): List<String> {
        return if (config.requireContacts && !isPermissionGranted(Manifest.permission.READ_CONTACTS)) {
            listOf(Manifest.permission.READ_CONTACTS)
        } else emptyList()
    }

    private fun Context.getCalendarPermissions(config: PermissionConfig): List<String> {
        val permissions = mutableListOf<String>()
        if (config.requireCalendar) {
            if (!isPermissionGranted(Manifest.permission.READ_CALENDAR)) {
                permissions.add(Manifest.permission.READ_CALENDAR)
            }
            if (!isPermissionGranted(Manifest.permission.WRITE_CALENDAR)) {
                permissions.add(Manifest.permission.WRITE_CALENDAR)
            }
        }
        return permissions
    }

    private fun Context.getPhonePermissions(config: PermissionConfig): List<String> {
        return if (config.requirePhone && !isPermissionGranted(Manifest.permission.CALL_PHONE)) {
            listOf(Manifest.permission.CALL_PHONE)
        } else emptyList()
    }

    fun Context.requestPermissions(
        config: PermissionConfig,
        launcher: ActivityResultLauncher<Array<String>>
    ) {
        val permissions = mutableListOf<String>()

        permissions.addAll(getStoragePermissions(config))
        permissions.addAll(getCameraPermissions(config))
        permissions.addAll(getNotificationPermissions(config))
        permissions.addAll(getLocationPermissions(config))
        permissions.addAll(getMicrophonePermissions(config))
        permissions.addAll(getContactsPermissions(config))
        permissions.addAll(getCalendarPermissions(config))
        permissions.addAll(getPhonePermissions(config))

        if (permissions.isNotEmpty()) {
            launcher.launch(permissions.toTypedArray())
        }
    }

    fun Context.arePermissionsGranted(config: PermissionConfig): Boolean {
        val permissionsToCheck = mutableListOf<String>()

        permissionsToCheck.addAll(getStoragePermissions(config))
        permissionsToCheck.addAll(getCameraPermissions(config))
        permissionsToCheck.addAll(getNotificationPermissions(config))
        permissionsToCheck.addAll(getLocationPermissions(config))
        permissionsToCheck.addAll(getMicrophonePermissions(config))
        permissionsToCheck.addAll(getContactsPermissions(config))
        permissionsToCheck.addAll(getCalendarPermissions(config))
        permissionsToCheck.addAll(getPhonePermissions(config))

        return permissionsToCheck.any {
            (!isPermissionGranted(it))
        }
    }
    private fun Context.showPermissionSettingsDialog(permissionType: PermissionType) {
        AlertDialog.Builder(this)
            .setTitle("${permissionType.permissionName} Permission Needed")
            .setMessage("This app requires the ${permissionType.permissionName} permission to function correctly. Please enable it in the settings.")
            .setPositiveButton("Go to Settings") { dialog, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = android.net.Uri.fromParts("package", packageName, null)
                }
                startActivity(intent)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    fun Context.checkAndShowSettingsDialogForPermission(
        permission: String,
        permissionType: PermissionType
    ) {
        if (isPermissionGranted(permission)) {
            return
        }

        val shouldShowRationale = when (this) {
            is Fragment -> this.shouldShowRequestPermissionRationale(permission)
            is Activity -> this.shouldShowRequestPermissionRationale(permission)
            else -> false
        }
        if (!shouldShowRationale) {
            showPermissionSettingsDialog(permissionType)
        }
    }
    }


