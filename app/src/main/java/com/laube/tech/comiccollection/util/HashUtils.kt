package com.laube.tech.comiccollection.util

import okhttp3.internal.and
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

//TODO : See if you can remove the OKHHTP reference or use another MD5 hash util
object HashUtils {
    fun md5(md5: String): String? {
        try {
            val md = MessageDigest.getInstance("MD5")
            val array = md.digest(md5.toByteArray())
            val sb = StringBuffer()
            for (i in array.indices) {
                sb.append(
                    Integer.toHexString(array[i] and 0xFF or 0x100).substring(
                        1,
                        3
                    )
                )
            }
            return sb.toString()
        } catch (e: NoSuchAlgorithmException) {
        }
        return null
    }
}