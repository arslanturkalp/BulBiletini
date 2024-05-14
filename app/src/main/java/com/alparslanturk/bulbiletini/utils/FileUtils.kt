package com.alparslanturk.bulbiletini.utils

import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class FileUtils {

    companion object {

        fun convertToBase64(filePath: String): String = Base64.encodeToString(File(filePath).readBytes(), Base64.NO_WRAP)

        fun convertToBase64(bitmap: Bitmap): String {
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 40, outputStream)
            return Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)
        }

        fun saveBase64File(context: Context, base64: String, mimeType: String = "pdf"): File? {
            val bytes = Base64.decode(base64, Base64.DEFAULT)
            val file = File(context.cacheDir, "MyHealth360.${mimeType}")
            val fileOutputStream: FileOutputStream
            try {
                fileOutputStream = FileOutputStream(file)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                return null
            }
            val bufferedOutputStream = BufferedOutputStream(fileOutputStream)
            try {
                bufferedOutputStream.write(bytes)
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            } finally {
                try {
                    bufferedOutputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return file
        }
    }
}