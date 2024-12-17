package com.a1developers.whatsappstatussaver.views.activities

import android.Manifest
import android.app.ActionBar
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.a1developers.whatsappstatussaver.R
import com.a1developers.whatsappstatussaver.databinding.ActivityDirectBinding
import com.a1developers.whatsappstatussaver.databinding.ActivityMainBinding
import com.a1developers.whatsappstatussaver.databinding.DialogGuideBinding
import com.a1developers.whatsappstatussaver.direct
import com.a1developers.whatsappstatussaver.utils.replaceFragment
import com.a1developers.whatsappstatussaver.views.fragments.FragmentSettings
import com.a1developers.whatsappstatussaver.views.fragments.FragmentStatus
import com.devatrii.statussaver.utils.Constants
import com.devatrii.statussaver.utils.SharedPrefKeys
import com.devatrii.statussaver.utils.SharedPrefUtils
import com.devatrii.statussaver.utils.slideFromStart
import com.devatrii.statussaver.utils.slideToEndWithFadeOut
import com.unity3d.ads.UnityAds

class MainActivity : AppCompatActivity() {





    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var activity: MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(binding.root)

//        UnityAds.initialize(this, 5745063, false)




       binding.direct.setOnClickListener {

startActivity(Intent(this,direct::class.java))
       }
        binding.apply {

//            val PERMISSION_REQUEST_CODE = 50
//
//            // Check if permission is already granted
//            if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED
//            ) {
//                // Permission is not granted, request it
//                ActivityCompat.requestPermissions(
//                    this@MainActivity,
//                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                    PERMISSION_REQUEST_CODE // Define this constant
//                )
//            } else {
//                // Permission is already granted, proceed with your logic
//                // ...
//            }
//            fun onRequestPermissionsResult(
//                requestCode: Int,
//                permissions: Array<out String>,
//                grantResults: IntArray
//            ) {
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//                when (requestCode) {
//                    PERMISSION_REQUEST_CODE -> {
//                        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                            // Permission granted, proceed with your logic
//                            // ...
//                        } else {
//                            // Permission denied, handle accordingly (e.g., show a message)
//                            // ...
//                        }
//                    }
//                }
//            }






//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                if(ContextCompat.checkSelfPermission(applicationContext,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
//
//                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100)
//                }
//
//            }
//
            //

//            requestPermission()
            splashlogic()



            val fragmentWhatsappStatus = FragmentStatus()
            val bundle = Bundle()
            bundle.putString(Constants.FRAGMENT_TYPE_KEY, Constants.TYPE_WHATSAPP_MAIN)
            replaceFragment(fragmentWhatsappStatus, bundle)
            bottomNavigationView.setOnItemSelectedListener {

                when (it.itemId) {
                    R.id.menu_status -> {

                        //whatsapp Status
                        val fragmentWhatsappStatus = FragmentStatus()
                        val bundle = Bundle()
                        bundle.putString(Constants.FRAGMENT_TYPE_KEY, Constants.TYPE_WHATSAPP_MAIN)
                        replaceFragment(fragmentWhatsappStatus, bundle)
                    }

                    R.id.menu_business_status -> {
                        //whatsapp Business status

                        val fragmentWhatsappStatus = FragmentStatus()
                        val bundle = Bundle()
                        bundle.putString(
                            Constants.FRAGMENT_TYPE_KEY,
                            Constants.TYPE_WHATSAPP_BUSINESS
                        )
                        replaceFragment(fragmentWhatsappStatus, bundle)

                    }

                    R.id.menu_settings -> {
                        //Whatsapp settings

                        replaceFragment(FragmentSettings())

                    }
                }
                return@setOnItemSelectedListener true

            }
        }

    }





    private val PERMISSION_REQUEST_CODE = 50
    private fun requestPermission() {

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            val isPermissionGranted =
                SharedPrefUtils.getPrefBoolean(
                    SharedPrefKeys.PREF_KEY_IS_PERMISSIONS_GRANTED,
                    false
                )
            if (!isPermissionGranted) {

                ActivityCompat.requestPermissions(this.activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),PERMISSION_REQUEST_CODE)
                Toast.makeText(this.activity, "Please Grant Permission", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {

            val isGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED

            if (isGranted) {
                SharedPrefUtils.getPrefBoolean(SharedPrefKeys.PREF_KEY_IS_PERMISSIONS_GRANTED, true)

            } else {

                SharedPrefUtils.getPrefBoolean(
                    SharedPrefKeys.PREF_KEY_IS_PERMISSIONS_GRANTED,
                    false
                )

            }
        }
    }

    private fun splashlogic() {
        binding.apply {
            splashLayout.cardView.slideFromStart()
            Handler(Looper.myLooper()!!).postDelayed({
                splashScreenHolder.slideToEndWithFadeOut()

                splashScreenHolder.visibility = View.GONE

            }, 2000)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = supportFragmentManager?.findFragmentById(R.id.fragment_container)
        fragment?.onActivityResult(requestCode, resultCode, data)
    }


}









