package com.example.reusablecomponents.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.media.MediaMetadataRetriever
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.reusablecomponents.BuildConfig
import com.example.reusablecomponents.R
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


fun Fragment.showSnackBar(message: String?) {
    if (message != null) view?.let { v ->
        Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
    }
}

fun Activity.showSnackBar(message: String?) {
    if (message != null) findViewById<View>(android.R.id.content)?.let { v ->
        Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
    }
}

fun ShapeableImageView.roundBottomCorners(value: Int = 30) {
    this.shapeAppearanceModel =
        this.shapeAppearanceModel.toBuilder().setBottomRightCorner(CornerFamily.ROUNDED, value.DP)
            .setBottomLeftCorner(CornerFamily.ROUNDED, value.DP).build()
}


fun Long.startHandler(handlerData: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed({
        handlerData.invoke()
    }, this)
}


@SuppressLint("SimpleDateFormat")
fun String.getFormattedTime(serverFormat: String, timezone: String? = null): Calendar {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = 0
    try {
        val sdf = SimpleDateFormat(serverFormat)
        if (!timezone.isNullOrBlank()) sdf.timeZone = TimeZone.getTimeZone(timezone)
        val testDate = sdf.parse(this)
        calendar.time = testDate ?: Date()
        return calendar
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return calendar
}


fun Calendar.getFormattedDate(format: String): String {
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.format(time)
}

fun Long.getCountDown(): Pair<Int, String> {
    val seconds: Long = this / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24
    return Pair(
        days.toInt(), String.format("%02d: %02d: %02d", hours % 24, minutes % 60, seconds % 60)
    )
}

fun ImageView.setImage(
    url: String?,
    placeholder: Drawable? = null,
    progress: ProgressBar? = null,
    onImageLoad: (() -> Unit)? = null
) {
    var progressBar = progress
    if (url != null) {
        val glide = Glide.with(this.context)
        val img =
            glide.load(url)//.signature(ObjectKey(UserPreferences(context).imageUpdateSignature?:""))
        placeholder?.let { img.placeholder(it) }
        progressBar?.visibility = View.VISIBLE
        img.listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?, model: Any?, target: Target<Drawable>, isFirstResource: Boolean
            ): Boolean {
                progressBar?.visibility = View.GONE
                progressBar = null
                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                onImageLoad?.invoke()
                progressBar?.visibility = View.GONE
                progressBar = null
                return false
            }

        }).into(this)
    }
}

fun inTryCatchBlock(catchBlock: (() -> Unit)? = null, tryBlock: () -> Unit) {
    try {
        tryBlock.invoke()
    } catch (e: Exception) {
        catchBlock?.invoke()
        e.printStackTrace()
    }
}

val Int.DP
    get() = Resources.getSystem().displayMetrics.density * this

fun EditText.getStringOrNull() = if (text.isBlank()) null else text.toString()
//fun TextView.getStringOrNull() = if (text.isBlank()) null else text.toString()

fun EditText.getString() = text.toString()
fun TextView.getString() = text.toString()

fun SwipeRefreshLayout.setUpViewAndListener(call: () -> Unit) {
    this.setColorSchemeResources(
        R.color.black, R.color.black, R.color.black, R.color.black
    )
    this.setProgressViewOffset(true, 0, 100)
    this.setOnRefreshListener { call.invoke() }
}

fun String.getMediaDuration(): Double {
    val mmr = MediaMetadataRetriever()
    mmr.setDataSource(this)
    val durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
    return durationStr?.toDouble() ?: (0.0 / 1000)
}


fun TextView.setVersionText() {
    text = context.getString(
        R.string.version_placeholder,
        BuildConfig.VERSION_NAME,
        BuildConfig.VERSION_CODE,
        BuildConfig.FLAVOR
    )
}

val String.isNewVersionAvailable: Boolean
    get() {
        val localVersion = BuildConfig.VERSION_NAME.split(".").map { it.toInt() }
        val remoteVersion = this.split(".").map { it.toInt() }
        var flag = false
        localVersion.forEachIndexed { index, i ->
            if (i < remoteVersion[index]) flag = true
        }
        return flag
    }

fun String.toMultilineWords(): String {
    return this.trim().replace(" ", "\n")
}