package com.a1developers.whatsappstatussaver.viewmodels.factories

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.a1developers.whatsappstatussaver.data.StatusRepo
import com.a1developers.whatsappstatussaver.models.MEDIA_TYPE_IMAGE
import com.a1developers.whatsappstatussaver.models.MEDIA_TYPE_VIDEO
import com.a1developers.whatsappstatussaver.models.MediaModel
import com.devatrii.statussaver.utils.Constants
import com.devatrii.statussaver.utils.SharedPrefKeys
import com.devatrii.statussaver.utils.SharedPrefUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatusViewModel(val repo : StatusRepo): ViewModel() {

    private val wpStatusLiveData get() = repo.WhatsappStatusesLiveData
    private val wpBusinessStatusLiveData get() = repo.WhatsappBusinessStatusesLiveData


//wp main
    val whatsappImagesLiveData = MutableLiveData<ArrayList<MediaModel>>()
    val whatsappVideosLiveData = MutableLiveData<ArrayList<MediaModel>>()

    //wp business

    val whatsappBusinessImagesLiveData = MutableLiveData<ArrayList<MediaModel>>()
    val whatsappBusinessVideosLiveData = MutableLiveData<ArrayList<MediaModel>>()

private var isPermissionGranted = false

    init {
        SharedPrefUtils.init(repo.context)

        val wpPermission = SharedPrefUtils.getPrefBoolean(SharedPrefKeys.PREF_KEY_WP_PERMISSION_GRANTED,false)
        val wpBusinessPermission = SharedPrefUtils.getPrefBoolean(SharedPrefKeys.PREF_KEY_WP_BUSINESS_PERMISSION_GRANTED,false)

        isPermissionGranted = wpPermission && wpBusinessPermission

        if (isPermissionGranted){

            CoroutineScope(Dispatchers.IO).launch {
                repo.getAllStatuses()
                repo.getAllStatuses(Constants.TYPE_WHATSAPP_BUSINESS)
            }

            CoroutineScope(Dispatchers.IO).launch {
                repo.getAllStatuses(Constants.TYPE_WHATSAPP_BUSINESS)
            }
        }

    }

    fun whatsAppStatuses(){

        CoroutineScope(Dispatchers.IO).launch {
          if (!isPermissionGranted){

               repo.getAllStatuses()
          }

            withContext(Dispatchers.Main){

                getWhatsappImgages()
                getWhatsappVideos()
            }
        }
    }

    fun getWhatsappImgages (){

        wpStatusLiveData.observe(repo.activity as LifecycleOwner){
            val tempList = ArrayList<MediaModel>()
            it.forEach { MediaModel ->

                if (MediaModel.type == MEDIA_TYPE_IMAGE){
                    tempList.add(MediaModel)
                }
            }

            whatsappImagesLiveData.postValue(tempList)
        }
    }

    fun getWhatsappVideos (){

        wpStatusLiveData.observe(repo.activity as LifecycleOwner){
            val tempList = ArrayList<MediaModel>()
            it.forEach { MediaModel ->

                if (MediaModel.type == MEDIA_TYPE_VIDEO){
                    tempList.add(MediaModel)
                }
            }

            whatsappVideosLiveData.postValue(tempList)
        }
    }
    fun whatsAppBusinessStatuses(){

        CoroutineScope(Dispatchers.IO).launch {
            if (!isPermissionGranted){

                repo.getAllStatuses(Constants.TYPE_WHATSAPP_BUSINESS)
            }

            withContext(Dispatchers.Main){

                getWhatsappBusinessImgages()
                getWhatsappBusinessVideos()
            }
        }
    }

    fun getWhatsappBusinessImgages (){

        wpBusinessStatusLiveData.observe(repo.activity as LifecycleOwner){
            val tempList = ArrayList<MediaModel>()
            it.forEach { MediaModel ->

                if (MediaModel.type == MEDIA_TYPE_IMAGE){
                    tempList.add(MediaModel)
                }
            }

            whatsappBusinessImagesLiveData.postValue(tempList)
        }
    }

    fun getWhatsappBusinessVideos (){

        wpBusinessStatusLiveData.observe(repo.activity as LifecycleOwner){
            val tempList = ArrayList<MediaModel>()
            it.forEach { MediaModel ->

                if (MediaModel.type == MEDIA_TYPE_VIDEO){
                    tempList.add(MediaModel)
                }
            }

            whatsappBusinessVideosLiveData.postValue(tempList)
        }
    }
}