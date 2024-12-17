package com.a1developers.whatsappstatussaver.views.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.a1developers.whatsappstatussaver.R
import com.a1developers.whatsappstatussaver.databinding.ItemMediaBinding
import com.a1developers.whatsappstatussaver.models.MEDIA_TYPE_IMAGE
import com.a1developers.whatsappstatussaver.models.MediaModel
import com.a1developers.whatsappstatussaver.utils.saveStatus
import com.a1developers.whatsappstatussaver.views.activities.ImagesPreview
import com.a1developers.whatsappstatussaver.views.activities.VideosPreview
import com.bumptech.glide.Glide
import com.devatrii.statussaver.utils.Constants

class MediaAdapter(val list: ArrayList<MediaModel>, val context: Context) :
    RecyclerView.Adapter<MediaAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemMediaBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(mediaModel: MediaModel) {
            binding.apply {
                Glide.with(context)
                    .load(mediaModel.pathUri.toUri())
                    .into(statusImgae)
                if (mediaModel.type == MEDIA_TYPE_IMAGE) {

                    statusPlay.visibility = View.GONE
                }
                val downloadImage = if (mediaModel.isDownloaded) {
                    R.drawable.ic_downloaded
                } else {
                    R.drawable.ic_download

                }
                statusDownload.setImageResource(downloadImage)

                cardStatus.setOnClickListener {
                    if (mediaModel.type == MEDIA_TYPE_IMAGE) {

                        Intent().apply {
                            putExtra(Constants.MEDIA_LIST_KEY,list)
                            putExtra(Constants.MEDIA_SCROLL_KEY,layoutPosition)
                            setClass(context,ImagesPreview::class.java)
                            context.startActivity(this)
                        }



                    } else {
                        Intent().apply {
                            putExtra(Constants.MEDIA_LIST_KEY,list)
                            putExtra(Constants.MEDIA_SCROLL_KEY,layoutPosition)
                            setClass(context,VideosPreview::class.java)
                            context.startActivity(this)
                        }

                    }
                }
                statusDownload.setOnClickListener {
                    val isDownloaded = context.saveStatus(mediaModel)
                    if (isDownloaded) {
//status downloaded
                        Toast.makeText(context,"Status Saved",Toast.LENGTH_LONG).show()
                        mediaModel.isDownloaded = true

                        statusDownload.setImageResource(R.drawable.ic_downloaded)


                    } else {

//enable to save
                    Toast.makeText(context,"Unable to Save",Toast.LENGTH_LONG).show()
                    }
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMediaBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = list[position]
        holder.bind(model)
    }
}