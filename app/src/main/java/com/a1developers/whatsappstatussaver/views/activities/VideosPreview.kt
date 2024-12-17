package com.a1developers.whatsappstatussaver.views.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.a1developers.whatsappstatussaver.databinding.ActivityVideosPreviewBinding
import com.a1developers.whatsappstatussaver.models.MediaModel
import com.a1developers.whatsappstatussaver.views.adapters.VideoPreviewAdapter
import com.devatrii.statussaver.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideosPreview : AppCompatActivity() {

    private val activity = this
    private lateinit var adapter: VideoPreviewAdapter
    private val TAG = "VideoPreview"



    private val binding : ActivityVideosPreviewBinding by lazy {
        ActivityVideosPreviewBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.apply {


            val list =
                intent.getSerializableExtra(Constants.MEDIA_LIST_KEY) as ArrayList<MediaModel>
            val scrollTo = intent.getIntExtra(Constants.MEDIA_SCROLL_KEY,0)
            adapter = VideoPreviewAdapter(list,activity)
            videoRecyclerView.adapter = adapter
            val pageSnapHelper = PagerSnapHelper()
            pageSnapHelper.attachToRecyclerView(videoRecyclerView)
            videoRecyclerView.scrollToPosition(scrollTo)


            videoRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING){

                        Log.d(TAG, "onScrollStateChanged: Dragging")
                        stopAllPlayers()



                    }
                }
        })
    }}

    private fun stopAllPlayers (){

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main){
                binding.apply {

                    for (i in 0 until videoRecyclerView.childCount){

                        val child = videoRecyclerView.getChildAt(i)
                        val viewHolder = videoRecyclerView.getChildViewHolder(child)
                        if (viewHolder is VideoPreviewAdapter.ViewHolder){

                            viewHolder.stopPlayers()

                        }
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        stopAllPlayers()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAllPlayers()
    }
}