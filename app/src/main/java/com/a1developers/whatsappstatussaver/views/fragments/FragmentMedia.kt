package com.a1developers.whatsappstatussaver.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.a1developers.whatsappstatussaver.databinding.FragmentMediaBinding
import com.a1developers.whatsappstatussaver.data.StatusRepo
import com.a1developers.whatsappstatussaver.models.MediaModel
import com.a1developers.whatsappstatussaver.viewmodels.factories.StatusViewModel
import com.a1developers.whatsappstatussaver.viewmodels.factories.StatusViewModelFactory
import com.a1developers.whatsappstatussaver.views.adapters.MediaAdapter
import com.devatrii.statussaver.utils.Constants


class FragmentMedia : Fragment() {

    private val binding: FragmentMediaBinding by lazy {
        FragmentMediaBinding.inflate(layoutInflater)
    }

    lateinit var viewModel: StatusViewModel
    lateinit var adapter: MediaAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            arguments?.let {

                val repo = StatusRepo(requireActivity())
                viewModel =ViewModelProvider(requireActivity(),StatusViewModelFactory(repo))[StatusViewModel::class.java]

                val mediaType: String = it.getString(Constants.MEDIA_TYPE_KEY, "")

                when (mediaType) {
                    Constants.MEDIA_TYPE_WHATSAPP_IMAGES -> {
                        viewModel.whatsappImagesLiveData.observe(requireActivity()) { unFilteredList ->
                            val filteredlist = unFilteredList.distinctBy { model ->

                                model.fileName

                            }

                            val list = ArrayList<MediaModel>()

                            filteredlist.forEach { model ->
                                list.add(model)

                            }
                            adapter = MediaAdapter(list, requireActivity())
                            mediaRecyclerView.adapter = adapter

                            if (list.size == 0){

                                tempmediatext.visibility = View.VISIBLE
                            }
                            else{
                                tempmediatext.visibility = View.GONE
                            }

                        }
                    }
                    Constants.MEDIA_TYPE_WHATSAPP_VIDEOS-> {
                        viewModel.whatsappVideosLiveData.observe(requireActivity()) { unFilteredList ->
                            val filteredlist = unFilteredList.distinctBy { model ->

                                model.fileName

                            }

                            val list = ArrayList<MediaModel>()

                            filteredlist.forEach { model ->
                                list.add(model)

                            }
                            adapter = MediaAdapter(list, requireActivity())
                            mediaRecyclerView.adapter = adapter

                            if (list.size == 0){

                                tempmediatext.visibility = View.VISIBLE
                            }
                            else{
                                tempmediatext.visibility = View.GONE
                            }


                        }
                    }
                    Constants.MEDIA_TYPE_WHATSAPP_BUSINESS_IMAGES -> {
                        viewModel.whatsappBusinessImagesLiveData.observe(requireActivity()) { unFilteredList ->
                            val filteredlist = unFilteredList.distinctBy { model ->

                                model.fileName

                            }

                            val list = ArrayList<MediaModel>()

                            filteredlist.forEach { model ->
                                list.add(model)

                            }
                            adapter = MediaAdapter(list, requireActivity())
                            mediaRecyclerView.adapter = adapter
                            if (list.size == 0){

                                tempmediatext.visibility = View.VISIBLE
                            }
                            else{
                                tempmediatext.visibility = View.GONE
                            }

                        }
                    }

                    Constants.MEDIA_TYPE_WHATSAPP_BUSINESS_VIDEOS -> {
                        viewModel.whatsappBusinessVideosLiveData.observe(requireActivity()) { unFilteredList ->
                            val filteredlist = unFilteredList.distinctBy { model ->

                                model.fileName

                            }

                            val list = ArrayList<MediaModel>()

                            filteredlist.forEach { model ->
                                list.add(model)

                            }
                            adapter = MediaAdapter(list, requireActivity())
                            mediaRecyclerView.adapter = adapter

                            if (list.size == 0){

                                tempmediatext.visibility = View.VISIBLE
                            }
                            else{
                                tempmediatext.visibility = View.GONE
                            }


                        }
                    }

                }

            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root


}
