package com.nickelfox.myfinaltest.data.network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import java.nio.charset.StandardCharsets
import javax.inject.Inject

//TODO : You can delete this class is not required
class FakeInterceptor @Inject constructor(@ApplicationContext private val context: Context) :
    Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //val response: Response? = null
        var responseString: String? = null
        val request = chain.request()
        val path = request.url.toUri().path
        val vals = path.split("/")

        if ((vals[vals.size - 2] + "/" + vals[vals.size - 1]) == ("/categories") && request.method == "GET") {
            responseString = getAssetJsonData("categories.json", context)
        } else if ((vals[vals.size - 2] + "/" + vals[vals.size - 1]) == ("/category_images") && request.method == "GET") {
            responseString = getAssetJsonData("categories_images.json", context)
        } else if (path.contains("models")) {
            responseString = getAssetJsonData("models.json", context)
        }

        return if (responseString != null) {
            Response.Builder()
                .code(200)
                .message(responseString)
                .request(request)
                .protocol(Protocol.HTTP_1_0)
                .addHeader("Content-Type", "application/json")
                .body(responseString.toResponseBody("application/json".toMediaTypeOrNull()))
                .build()
        } else {
            chain.proceed(request)
        }


    }


    companion object {

        fun getAssetJsonData(fileName: String, context: Context): String? {
            val json: String
            try {
                val `is` = context.assets.open(fileName)
                val size = `is`.available()
                val buffer = ByteArray(size)
                `is`.read(buffer)
                `is`.close()
                json = String(buffer, StandardCharsets.UTF_8)
            } catch (ex: IOException) {
                ex.printStackTrace()
                return null
            }

            return json
        }
    }
}
