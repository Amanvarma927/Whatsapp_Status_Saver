package com.a1developers.whatsappstatussaver.views.activities

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.a1developers.whatsappstatussaver.databinding.ActivityImagesPreviewBinding
import com.a1developers.whatsappstatussaver.models.MediaModel
import com.a1developers.whatsappstatussaver.views.adapters.ImagePreviewAdapter
import com.devatrii.statussaver.utils.Constants
import com.unity3d.ads.IUnityAdsInitializationListener
import com.unity3d.ads.UnityAds
import com.unity3d.ads.UnityAdsShowOptions
import com.unity3d.ads.IUnityAdsLoadListener
import com.unity3d.ads.IUnityAdsShowListener



class ImagesPreview : AppCompatActivity() {

    private val activity = this
     lateinit var adapter: ImagePreviewAdapter
    private val imagesView = this
//    private val adapter : ImagesPreview
//private val TAG = "ImagePreview"




    private val binding: ActivityImagesPreviewBinding by lazy {
        ActivityImagesPreviewBinding.inflate(layoutInflater)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)





        binding.apply {

val list =  intent.getSerializableExtra(Constants.MEDIA_LIST_KEY) as ArrayList<MediaModel>
            adapter = ImagePreviewAdapter(list,activity)

            val scrollTo =intent.getIntExtra(Constants.MEDIA_SCROLL_KEY,0)
            imagesView.adapter = adapter
            imagesView.currentItem = scrollTo


        }

    }
}