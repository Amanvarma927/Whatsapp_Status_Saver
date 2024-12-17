package com.a1developers.whatsappstatussaver.views.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.RecyclerView
import com.a1developers.whatsappstatussaver.R
import com.a1developers.whatsappstatussaver.databinding.ItemVideoPreviewBinding
import com.a1developers.whatsappstatussaver.models.MediaModel
import com.a1developers.whatsappstatussaver.utils.saveStatus

class VideoPreviewAdapter(private val list: ArrayList<MediaModel>, val context: Context) :
    RecyclerView.Adapter<VideoPreviewAdapter.ViewHolder>() {


    inner class ViewHolder(private val binding: ItemVideoPreviewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(mediaModel: MediaModel) {
            binding.apply {

                    val player = ExoPlayer.Builder(context).build()
                playerView.player = player

                val mediaItem = MediaItem.fromUri(mediaModel.pathUri)
                player.setMediaItem(mediaItem)
                player.prepare()
//                player.play()


                val downloadImage = if (mediaModel.isDownloaded) {
                    R.drawable.ic_downloaded
                } else {
                    R.drawable.ic_download

                }
                tools.statusdownload.setImageResource(downloadImage)

                tools.share.setOnClickListener(View.OnClickListener {


                    val uri = Uri.parse(list.get(position).pathUri)
                    val shareintent = Intent(Intent.ACTION_SEND)
                    shareintent.setType("video/*")
                    shareintent.putExtra(Intent.EXTRA_STREAM,uri)
                    shareintent.putExtra(Intent.EXTRA_TEXT,"Save All Status at one Click :https://play.google.com/store/apps/details?id= ${context.packageName}")

                    context.startActivity(Intent.createChooser(shareintent,"Share Video"))
                })

                tools.download.setOnClickListener {
                    val isDownloaded = context.saveStatus(mediaModel)
                    if (isDownloaded) {
//status downloaded
                        Toast.makeText(context,"Status Saved", Toast.LENGTH_LONG).show()
                        mediaModel.isDownloaded = true

                        tools.statusdownload.setImageResource(R.drawable.ic_downloaded)


                    } else {

//enable to save
                        Toast.makeText(context,"Unable to Save", Toast.LENGTH_LONG).show()
                    }
                }

            }
        }

        fun  stopPlayers(){
            binding.playerView.player?.stop()
        }

    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(ItemVideoPreviewBinding.inflate(LayoutInflater.from(context), parent, false))

        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        holder.bind(model)
    }

    override fun getItemCount() = list.size



}