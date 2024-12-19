package com.example.permisssions

import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

class PermissionManager(private val context: FragmentActivity) {

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var callback: PermissionCallback? = null
    private var codeBlock: (() -> Unit)? = null

    init {
        register()
    }

    interface PermissionCallback {
        fun onPermissionsDenied(deniedPermissions: List<String>)
    }

    fun register() {
        permissionLauncher = context.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            handlePermissionsResult(permissions)
        }
    }

    fun requestPermissions(
        permissions: Array<String>,
        callback: PermissionCallback,
        codeBlock: (() -> Unit)? = null
    ) {
        this.callback = callback
        val deniedPermissions = getDeniedPermissions(permissions)

        if (deniedPermissions.isEmpty()) {
            codeBlock?.invoke()
        } else {
            this.codeBlock = codeBlock
            permissionLauncher.launch(deniedPermissions)
        }
    }

    private fun handlePermissionsResult(permissions: Map<String, Boolean>) {
        val deniedPermissions = permissions.filter { !it.value }.keys.toList()
        if (deniedPermissions.isEmpty()) {
            codeBlock?.invoke()
        } else {
            callback?.onPermissionsDenied(deniedPermissions)
        }
    }

    fun getDeniedPermissions(permissions: Array<String>): Array<String> {
        return permissions.filter {
            ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()
    }

    fun getGrantedPermissions(permissions: Array<String>): List<String> {
        return permissions.filter {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun shouldShowRequestPermissionRationales(permissions: Array<String>): List<String> {
        return permissions.filter { context.shouldShowRequestPermissionRationale(it) }
    }

    companion object {

        object Location {
            const val FINE = android.Manifest.permission.ACCESS_FINE_LOCATION
            const val COARSE = android.Manifest.permission.ACCESS_COARSE_LOCATION
        }

        object Camera {
            const val CAMERA = android.Manifest.permission.CAMERA
        }

        object Storage {
            const val READ = android.Manifest.permission.READ_EXTERNAL_STORAGE
            const val WRITE = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        }

        @RequiresApi(Build.VERSION_CODES.S)
        object Bluetooth {
            const val BLUETOOTH = android.Manifest.permission.BLUETOOTH
            const val BLUETOOTH_ADMIN = android.Manifest.permission.BLUETOOTH_ADMIN
            const val BLUETOOTH_SCAN = android.Manifest.permission.BLUETOOTH_SCAN
            const val BLUETOOTH_CONNECT = android.Manifest.permission.BLUETOOTH_CONNECT
            const val BLUETOOTH_ADVERTISE = android.Manifest.permission.BLUETOOTH_ADVERTISE
        }

        object Other {
            const val NOTIFICATION = "android.permission.POST_NOTIFICATIONS"

            @RequiresApi(Build.VERSION_CODES.TIRAMISU)
            const val NEARBY_WIFI_DEVICES = android.Manifest.permission.NEARBY_WIFI_DEVICES
        }

        object Contacts {
            const val READ_CONTACTS = android.Manifest.permission.READ_CONTACTS
            const val WRITE_CONTACTS = android.Manifest.permission.WRITE_CONTACTS
            const val GET_ACCOUNTS = android.Manifest.permission.GET_ACCOUNTS
        }

        object Phone {
            const val READ_PHONE_STATE = android.Manifest.permission.READ_PHONE_STATE
            const val CALL_PHONE = android.Manifest.permission.CALL_PHONE
            const val READ_CALL_LOG = android.Manifest.permission.READ_CALL_LOG
            const val WRITE_CALL_LOG = android.Manifest.permission.WRITE_CALL_LOG
            const val ADD_VOICEMAIL = android.Manifest.permission.ADD_VOICEMAIL
            const val USE_SIP = android.Manifest.permission.USE_SIP
            const val PROCESS_OUTGOING_CALLS = android.Manifest.permission.PROCESS_OUTGOING_CALLS
        }

        object Calendar {
            const val READ_CALENDAR = android.Manifest.permission.READ_CALENDAR
            const val WRITE_CALENDAR = android.Manifest.permission.WRITE_CALENDAR
        }

        object Sensors {
            const val BODY_SENSORS = android.Manifest.permission.BODY_SENSORS
        }

        object Sms {
            const val SEND_SMS = android.Manifest.permission.SEND_SMS
            const val RECEIVE_SMS = android.Manifest.permission.RECEIVE_SMS
            const val READ_SMS = android.Manifest.permission.READ_SMS
            const val RECEIVE_WAP_PUSH = android.Manifest.permission.RECEIVE_WAP_PUSH
            const val RECEIVE_MMS = android.Manifest.permission.RECEIVE_MMS
        }

        object Microphone {
            const val RECORD_AUDIO = android.Manifest.permission.RECORD_AUDIO
        }
    }
}
