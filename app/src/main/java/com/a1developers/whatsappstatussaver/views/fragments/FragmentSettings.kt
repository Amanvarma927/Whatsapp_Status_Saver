package com.a1developers.whatsappstatussaver.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import com.a1developers.whatsappstatussaver.databinding.FragmentSettingsBinding
import com.a1developers.whatsappstatussaver.models.SettingsModel
import com.a1developers.whatsappstatussaver.views.adapters.SettingsAdapter


class FragmentSettings : Fragment() {

    private val binding: FragmentSettingsBinding by lazy {
        FragmentSettingsBinding.inflate(layoutInflater)
    }
    private val list = ArrayList<SettingsModel>()
    private val adapter by lazy {
    SettingsAdapter(list,requireActivity())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            settingRecyclerView.adapter  = adapter

            list.add(
                SettingsModel(
                    title = "How To Use",
                    desc = "Know how to download Statuses"
                )
            )
            list.add(
                SettingsModel(
                    title = "Save in Folder",
                    desc = "/internalstorage/Documents/${"Whatsapp Status Saver"}"
                )
            )
            list.add(
                SettingsModel(
                    title = "Disclaimer",
                    desc = "Read our Disclaimer"
                )
            )
            list.add(
                SettingsModel(
                    title = "Privacy Policy",
                    desc = "Read our Terms & Conditions"
                )
            )
            list.add(
                SettingsModel(
                    title = "Share our App",
                    desc = "Sharing is Caring❤️"
                )
            )
            list.add(
                SettingsModel(
                    title = "Rate Us!",
                    desc = "Please give us 5 Star Ratings on Play Store"
                )
            )


        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =  binding.root


}