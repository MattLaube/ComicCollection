package com.laube.tech.comiccollection.util

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MarvelUtil {
    companion object {
        @JvmStatic
        fun getCoverLink(path: String, size: String, extension: String): String {
            val updatedUrl: String
            if (path.startsWith("http:", true)) {
                updatedUrl = path.replace("http:", "https:")
            } else {
                updatedUrl = path
            }
            return "$updatedUrl/$size.$extension"
        }

        @JvmStatic
        fun convertJSONDate(givenDate: String): String {
            var finalDate = ""
            try {
                val inDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH)
                val outDateFormat = SimpleDateFormat("MMMM, yyyy", Locale.ENGLISH)

                val actualDate = inDateFormat.parse(givenDate)
                finalDate = outDateFormat.format(actualDate)
            }catch (e : Exception){
                finalDate = "Not Available"
            }
            return finalDate
        }

    }
}