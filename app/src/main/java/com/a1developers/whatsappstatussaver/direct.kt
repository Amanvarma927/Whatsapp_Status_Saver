



package com.a1developers.whatsappstatussaver

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.a1developers.whatsappstatussaver.databinding.ActivityDirectBinding
import com.a1developers.whatsappstatussaver.databinding.ActivityMainBinding
import java.net.URLEncoder

class direct : AppCompatActivity() {


    private val binding: ActivityDirectBinding by lazy {
        ActivityDirectBinding.inflate(layoutInflater)
    }
    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        fun sendMessageToWhatsApp(phoneNumber: String, message: String = "") {
            val whatsappIntent = Intent(Intent.ACTION_VIEW)
            val uri = Uri.parse("whatsapp://send?phone=$phoneNumber&text=${Uri.encode(message)}")
            whatsappIntent.data = uri

            try {
                startActivity(whatsappIntent)
            } catch (e: ActivityNotFoundException) {
                // Handle WhatsApp not installed
                Toast.makeText(this, "WhatsApp is not installed", Toast.LENGTH_SHORT).show()
            }
        }


        binding.sendmsg.setOnClickListener {
            val phoneNumber = binding.number.text.toString()
            val message = binding.msg.text.toString()

            sendMessageToWhatsApp(phoneNumber, message)

        }

// ... (sendMessageToWhatsApp function from previous response) ...


    }}
//            binding.sendmsg.setOnClickListener {
//
//                if (binding.number.text.isNotEmpty() && binding.msg.text.isNotEmpty()) {
//
//                    val packagemanager = packageManager
//                    val i = Intent(Intent.ACTION_VIEW)
//                    val url =
//                        "https://api.whatsapp.com/send?phone=" + binding.number.text.toString() + "&text=" + binding.msg.text.toString() + "&text="+ URLEncoder.encode(binding.msg.text.toString(), "UTF-8")
//                    i.setPackage("com.whatsapp")
//                    i.data = Uri.parse(url)
//                    if (i.resolveActivity(packagemanager) != null) {
//                        startActivity(i)
//                    }
//
//
//                }
//                else{
//
//                    Toast.makeText(this@direct, "Enter Number and Message", Toast.LENGTH_SHORT).show()
//
//
//                }
//            }
//        }

