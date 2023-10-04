package com.geekbrains.jpgtopngconverter.mvp.presenter

import android.net.Uri
import android.content.ContentResolver
import android.provider.MediaStore

object DataUtils {

    fun getValidatePath(textPath: CharSequence?) : String?  {
        textPath?.toString()?.let {
            return if (!it.isEmpty()) it else null
        }
        return null
    }

    fun getImageFormat(imagePath: CharSequence?) : String? {
        imagePath?.let {
            val dotIndex = it.lastIndexOf('.')
            return if (dotIndex != -1) it.substring(dotIndex + 1) else null
        }
        return null
    }

    fun getPathFromUri(contentResolver: ContentResolver, contentUri: Uri): String? {
        var res: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(contentUri, projection,
            null, null, null);
        if (cursor != null) {
            cursor.moveToFirst()
            val columnIndex = cursor.getColumnIndex(projection[0])
            columnIndex.let {
                res = cursor.getString(columnIndex)
            }
            cursor.close()
        }
        return res
    }
}

