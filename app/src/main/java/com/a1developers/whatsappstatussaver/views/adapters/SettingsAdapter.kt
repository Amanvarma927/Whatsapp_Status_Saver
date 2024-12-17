package com.a1developers.whatsappstatussaver.views.adapters

import android.app.ActionBar
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a1developers.whatsappstatussaver.R
import com.a1developers.whatsappstatussaver.databinding.DialogGuideBinding
import com.a1developers.whatsappstatussaver.databinding.ItemSettingsBinding
import com.a1developers.whatsappstatussaver.models.SettingsModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SettingsAdapter(private val list: ArrayList<SettingsModel>, val context: Context) :
    RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {


    inner class ViewHolder(private val binding: ItemSettingsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: SettingsModel, position: Int) {

            binding.apply {
                settings.text = model.title
                settingsDesc.text = model.desc

                root.setOnClickListener {
                    when (position) {
                        0 -> {
                            //how to use waala item

                            val dialog = Dialog(context)
                            val dialogBinding =
                                DialogGuideBinding.inflate((context as Activity).layoutInflater)
                            dialogBinding.okayHow.setOnClickListener {
                                dialog.dismiss()
                            }
                            dialog.setContentView(dialogBinding.root)

                            dialog.window?.setLayout(
                                ActionBar.LayoutParams.MATCH_PARENT,
                                ActionBar.LayoutParams.WRAP_CONTENT
                            )
                            dialog.show()
                        }

                        2 -> {
                            MaterialAlertDialogBuilder(context).apply {
                                setTitle("Disclaimer")
                                setMessage("WhatsApp Status Saver is not affiliated with WhatsApp. It helps to save WhatsApp status images and videos." +
                                    "Any use of downloaded content by the user is not the responsibility of this app." +
                                            "All the trademarks are the rights of their respective owners.\n" +
                                            "We respect the copyright of the owners. So please DO NOT download or repost the videos, photos and media clips without owners permission")
                                show()
                            }
                        }

                        3 -> {
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.e-droid.net/privacy.php?ida=3408419&idl=en")
                            ).apply {
                                context.startActivity(this)
                            }
                        }

                        4 -> {
                            Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(
                                    Intent.EXTRA_SUBJECT,
                                    context.getString(R.string.app_name)
                                )
                                putExtra(Intent.EXTRA_TEXT,"This App is so cool please download it :https://play.google.com/store/apps/details?id= ${context.packageName}")
                                context.startActivity(this)
                            }
                        }

                        5 -> {

                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=" + context.packageName)
                            ).apply {
                                context.startActivity(this)

                            }
                        }

                    }

                }
            }

        }}

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): SettingsAdapter.ViewHolder {
            return ViewHolder(
                ItemSettingsBinding.inflate(
                    LayoutInflater.from(context),
                    parent,
                    false
                )
            )

        }

        override fun onBindViewHolder(holder: SettingsAdapter.ViewHolder, position: Int) {
            holder.bind(model = list[position], position)
        }

        override fun getItemCount() = list.size
    }