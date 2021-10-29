package com.oui.parisiproject.common

import android.os.Build
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.time.OffsetDateTime
import java.time.ZoneOffset
import javax.inject.Inject

class Keys @Inject constructor(){

    var request_time: String = ""
    var key: String = ""

    fun getRequestTime(): String {
        var now: OffsetDateTime? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            now = OffsetDateTime.now(ZoneOffset.UTC)
            val time = now.toString().substring(0, now.toString().length - 5)
            request_time = "$time+0000"
        }
        return request_time
    }

    fun md5(s: String): String? {
        val MD5 = "MD5"
        try {
            // Create MD5 Hash
            val digest = MessageDigest
                .getInstance(MD5)
            digest.update(s.toByteArray())
            val messageDigest = digest.digest()

            // Create Hex String
            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2) h = "0$h"
                key = hexString.append(h).toString()
            }
            return key
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }

}