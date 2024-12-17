package com.a1developers.whatsappstatussaver.data

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.MutableLiveData
import com.a1developers.whatsappstatussaver.models.MEDIA_TYPE_IMAGE
import com.a1developers.whatsappstatussaver.models.MEDIA_TYPE_VIDEO
import com.a1developers.whatsappstatussaver.models.MediaModel
import com.a1developers.whatsappstatussaver.utils.getFileExtension
import com.a1developers.whatsappstatussaver.utils.isStatusExist
import com.devatrii.statussaver.utils.Constants
import com.devatrii.statussaver.utils.SharedPrefKeys
import com.devatrii.statussaver.utils.SharedPrefUtils

class  StatusRepo(val context: Context) {


    val WhatsappStatusesLiveData = MutableLiveData<ArrayList<MediaModel>>()
    val WhatsappBusinessStatusesLiveData = MutableLiveData<ArrayList<MediaModel>>()


    val activity = context as Activity

    private val wpStatusesList = ArrayList<MediaModel>()
    private val wpBusinessStatusesList = ArrayList<MediaModel>()


    fun getAllStatuses(WhatsappType: String = Constants.TYPE_WHATSAPP_MAIN) {
        val treeUri = when (WhatsappType) {

            Constants.TYPE_WHATSAPP_MAIN -> {

                SharedPrefUtils.getPrefString(SharedPrefKeys.PREF_KEY_WP_TREE_URI, "")?.toUri()!!


            }

            else -> {
                SharedPrefUtils.getPrefString(SharedPrefKeys.PREF_KEY_WP_BUSINESS_TREE_URI, "")
                    ?.toUri()!!

            }
        }
        activity.contentResolver.takePersistableUriPermission(
            treeUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
        )

        val fileDocument = DocumentFile.fromTreeUri(activity, treeUri)

        fileDocument?.let {
            it.listFiles().forEach { file ->
                Log.d(TAG,"getAllStatuses: ${file.name}")
                if (file.name != ".nomdeia" && file.isFile) {

                    val isDownloaded = context.isStatusExist(file.name!!)
                    Log.d("Status Repo","getAllStatuses: Extension: ${getFileExtension(file.name!!)}||${file.name}")
                    val type = if (getFileExtension(file.name!!) == "mp4") {
                        MEDIA_TYPE_VIDEO

                    } else {

                        MEDIA_TYPE_IMAGE

                    }

                    val model = MediaModel(
                        pathUri = file.uri.toString(),
                        fileName = file.name!!,
                        type = type,
                        isDownloaded = isDownloaded
                    )
                    when (WhatsappType) {
                        Constants.TYPE_WHATSAPP_MAIN -> {
                            wpStatusesList.add(model)


                        }

                        else -> {
                            wpBusinessStatusesList.add(model)
                        }
                    }

                }
            }

        }

        when (WhatsappType) {
            Constants.TYPE_WHATSAPP_MAIN -> {
                WhatsappStatusesLiveData.postValue(wpStatusesList)


            }

            else -> {
                WhatsappBusinessStatusesLiveData.postValue(wpBusinessStatusesList)

            }
        }
    }}