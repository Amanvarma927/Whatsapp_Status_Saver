package com.a1developers.whatsappstatussaver.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import com.a1developers.whatsappstatussaver.R
import com.a1developers.whatsappstatussaver.models.MediaModel
import com.anggrayudi.storage.file.toRawFile
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.channels.FileChannel

//import kotlin.js.ExperimentalJsFileName

fun Context.isStatusExist(fileName: String): Boolean {

    val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)

    val file = File("${downloadDir}/${getString(R.string.app_name)}", fileName)

    return file.exists()
}

fun getFileExtension(fileName: String): String {

    val lastDotIndex = fileName.lastIndexOf(".")

    if (lastDotIndex >= 0 && lastDotIndex < fileName.length - 1) {

        return fileName.substring(lastDotIndex + 1)

    }
    return ""
}

fun Context.saveStatus(model: MediaModel): Boolean {
    if (isStatusExist(model.fileName)) {
        return true
    }

    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {

return savestatusbeforeQ(this, Uri.parse(model.pathUri))

    }
    val extension = getFileExtension(model.fileName)
    val mimeType = "${model.type}/$extension"
    val inputStream = contentResolver.openInputStream(model.pathUri.toUri())
    try {
        val values = ContentValues()
        values.apply {
            put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
            put(MediaStore.MediaColumns.DISPLAY_NAME, model.fileName)
            put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                Environment.DIRECTORY_DOCUMENTS + "/" + getString(R.string.app_name)
            )
        }
        val uri = contentResolver.insert(
            MediaStore.Files.getContentUri("external"),
            values
        )
        uri?.let {
            val outputStream = contentResolver.openOutputStream(it)
            if (inputStream != null) {
                outputStream?.write(inputStream.readBytes())
            }
            outputStream?.close()
            inputStream?.close()
            return true
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}

private fun savestatusbeforeQ(context: Context, uri: Uri): Boolean {

    //converting doc file to file
    try {

        val documentfile = DocumentFile.fromTreeUri(context, uri)
        if (documentfile != null) {

            val sourcefile = documentfile.toRawFile(context)?.takeIf { f2 ->
                f2.canRead()
            }

            val destinationfile = sourcefile?.let { sourceF ->
                File(
                    "${Environment.getExternalStorageDirectory()}/Documents/${context.getString(R.string.app_name)}",
                    sourceF.name
                )

            }
//making directories & files
            destinationfile?.let { destfile ->
                if (!destfile.parentFile?.exists()!!) {

                    destfile.mkdirs()
                }
                if (!destfile.exists()) {

                    destfile.createNewFile()
                }

                //copy content from source file to dest file

                val source:FileChannel = FileInputStream(sourcefile).channel
                val destination = FileOutputStream(destfile).channel

                destination.transferFrom(source, 0, source.size())
                source.close()
                destination.close()


                return true

            }

        }
        return false
    } catch (e: Exception) {

        e.printStackTrace()
        return false
    }
}













