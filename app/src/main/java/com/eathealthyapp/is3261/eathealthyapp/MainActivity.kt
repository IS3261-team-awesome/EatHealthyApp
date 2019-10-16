package com.eathealthyapp.is3261.eathealthyapp

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    lateinit var myCamera : MyCamera

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1 = findViewById<View>(R.id.button1)
        button1.setOnClickListener {
            val intent = Intent(this, ActivityTopup::class.java)
            startActivity(intent)
        }

        val button2 = findViewById<View>(R.id.button2)
        button2.setOnClickListener {
            myCamera = MyCamera(this)
            myCamera.dispatchTakePictureIntent()
        }

        val button3 = findViewById<View>(R.id.button3)
        button3.setOnClickListener {
            val intent = Intent(this, ActivityFoodDetail::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // After user took a photo
        if (requestCode == myCamera.TAKE_PHOTO_REQUEST && resultCode == Activity.RESULT_OK) run {
            // will try to decode qr code here

            // delete photo after decoding process

            // direct data to somewhere else to be saved
        }
    }
}
