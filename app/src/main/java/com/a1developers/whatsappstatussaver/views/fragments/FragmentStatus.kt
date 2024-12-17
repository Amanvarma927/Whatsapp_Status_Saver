package com.a1developers.whatsappstatussaver.views.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.a1developers.whatsappstatussaver.databinding.FragmentStatusBinding
import com.a1developers.whatsappstatussaver.data.StatusRepo
import com.a1developers.whatsappstatussaver.utils.getFolderPermission
import com.a1developers.whatsappstatussaver.viewmodels.factories.StatusViewModel
import com.a1developers.whatsappstatussaver.viewmodels.factories.StatusViewModelFactory
import com.a1developers.whatsappstatussaver.views.adapters.MediaViewPagerAdapter
import com.devatrii.statussaver.utils.Constants
import com.devatrii.statussaver.utils.SharedPrefKeys
import com.devatrii.statussaver.utils.SharedPrefUtils
import com.google.android.material.tabs.TabLayoutMediator

class FragmentStatus : Fragment() {

    private val binding: FragmentStatusBinding by lazy {
        FragmentStatusBinding.inflate(layoutInflater)
    }
    private lateinit var type: String
    private val WHATSAPP_REQUEST_CODE = 101
    private val WHATSAPP_BUSINESS_REQUEST_CODE = 102

    private val viewPagerTitles = arrayListOf("Images","Videos")

    lateinit var  viewModel :StatusViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            arguments?.let {


                val repo  = StatusRepo(requireActivity())
                viewModel = ViewModelProvider(
                    requireActivity(),
                    StatusViewModelFactory(repo)
                  )[StatusViewModel::class.java]



                type = it.getString(Constants.FRAGMENT_TYPE_KEY, "")
//                temptext.text = type
                when (type) {
                    Constants.TYPE_WHATSAPP_MAIN -> {

                        val isPermissionGranted = SharedPrefUtils.getPrefBoolean(
                            SharedPrefKeys.PREF_KEY_WP_PERMISSION_GRANTED, false
                        )
                        if (isPermissionGranted) {

                            getWhatsappStatuses()


                            binding.swiperefreshlayout.setOnRefreshListener{
                                refreshStatuses()
                            }
                        }


                        permissionLayout.btnPermission.setOnClickListener {

                            getFolderPermission(
                                context = requireActivity(),
                                REQUEST_CODE = WHATSAPP_REQUEST_CODE,
                                initialUri = Constants.getWhatsappUri()
                            )
                        }

                        val viewPagerAdapter = MediaViewPagerAdapter(requireActivity())
                        statusViewPager.adapter = viewPagerAdapter
                        TabLayoutMediator(tabLayout,statusViewPager){tab,pos->
                            tab.text = viewPagerTitles[pos]
                        }.attach()

                        binding.swiperefreshlayout.setOnRefreshListener{
                            refreshStatuses()
                        }

                    }


                    Constants.TYPE_WHATSAPP_BUSINESS -> {
//
                        val isPermissionGranted = SharedPrefUtils.getPrefBoolean(
                                SharedPrefKeys.PREF_KEY_WP_BUSINESS_PERMISSION_GRANTED, false
                        )
                        if (isPermissionGranted) {

                            getWhatsappBusinessStatuses()


                            binding.swiperefreshlayout.setOnRefreshListener{
                                refreshStatuses()
                            }
                        }
                        permissionLayout.btnPermission.setOnClickListener {

                            getFolderPermission(
                                context = requireActivity(),
                                REQUEST_CODE = WHATSAPP_BUSINESS_REQUEST_CODE,
                                initialUri = Constants.getWhatsappBusinessUri()
                            )

                        }
                        val viewPagerAdapter = MediaViewPagerAdapter(requireActivity(),
                            imagesType = Constants.MEDIA_TYPE_WHATSAPP_BUSINESS_IMAGES,
                            videosType = Constants.MEDIA_TYPE_WHATSAPP_BUSINESS_VIDEOS)
                        statusViewPager.adapter = viewPagerAdapter
                        TabLayoutMediator(tabLayout,statusViewPager){tab,pos->
                            tab.text = viewPagerTitles[pos]
                        }.attach()

                    }
                }
            }
        }

    }


    fun refreshStatuses(){

        when(type){

            Constants.TYPE_WHATSAPP_MAIN->{
                Toast.makeText(requireActivity(),"Refreshing Whatsapp Statuses",Toast.LENGTH_LONG).show()
                getWhatsappStatuses()

            }
            else->{
                Toast.makeText(requireActivity(),"Refreshing Whatsapp Business Statuses",Toast.LENGTH_LONG).show()
                getWhatsappBusinessStatuses()

            }

        }

        Handler(Looper.myLooper()!!).postDelayed({

        binding.swiperefreshlayout.isRefreshing = false

        },2000)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = binding.root


    fun getWhatsappStatuses() {
        binding.permissionLayoutHolder.visibility = View.GONE
//        viewModel.getWhatsappImgages()
//        viewModel.getWhatsappVideos()
        viewModel.whatsAppStatuses()
    }

    fun getWhatsappBusinessStatuses() {

        binding.permissionLayoutHolder.visibility = View.GONE
//        viewModel.getWhatsappBusinessImgages()
//        viewModel.getWhatsappBusinessVideos()
        viewModel.whatsAppBusinessStatuses()
    }


//    override fun onResume() {
////        super.onResume()
//        getWhatsappStatuses()
//        getWhatsappBusinessStatuses()
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == AppCompatActivity.RESULT_OK) {

            val treeUri = data?.data!!
            requireActivity().contentResolver.takePersistableUriPermission(
                treeUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            if (requestCode == WHATSAPP_REQUEST_CODE) {

                //WHATSAPP LOGIC HERE
                SharedPrefUtils.putPrefString(
                    SharedPrefKeys.PREF_KEY_WP_TREE_URI, treeUri.toString()
                )
                SharedPrefUtils.putPrefBoolean(SharedPrefKeys.PREF_KEY_WP_PERMISSION_GRANTED, true)
//
                getWhatsappStatuses()

            } else if (requestCode == WHATSAPP_BUSINESS_REQUEST_CODE) {

                //WHATSAPP BUSINESS LOGIC HERE
                SharedPrefUtils.putPrefString(
                    SharedPrefKeys.PREF_KEY_WP_BUSINESS_TREE_URI, treeUri.toString()
                )
                SharedPrefUtils.putPrefBoolean(
                    SharedPrefKeys.PREF_KEY_WP_BUSINESS_PERMISSION_GRANTED, true
                )

                getWhatsappBusinessStatuses()


            }
        }


    }
}
