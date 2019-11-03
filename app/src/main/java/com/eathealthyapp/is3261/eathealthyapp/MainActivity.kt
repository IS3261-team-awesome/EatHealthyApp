package com.eathealthyapp.is3261.eathealthyapp

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.View
import android.widget.TextView
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {
    val PROCESS_QRCODE_REQUEST = 1

    var allPermissionsGrantedFlag: Int = 0

    private val permissionList = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Camera is a dangerous permission, we need to re-ask permission here
        // if newer or equal to marshamellow version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (allPermissionsEnabled()) {
                // all permissions granted, no need to do anything
                allPermissionsGrantedFlag = 1
            } else {
                setupMultiplePermissions()
            }
            // if older android versions dunnid do this
        } else {
            // it must be older than Marshmallow. As long as AndroidManifest.xml
            // specifies the permissions, nothing else needs to be done
            allPermissionsGrantedFlag = 1
        }

        // Set listeners for buttons
        val button1 = findViewById<View>(R.id.button1)
        button1.setOnClickListener {
            val intent = Intent(this, ActivityTopup::class.java)
            startActivity(intent)
        }
        val button2 = findViewById<View>(R.id.button2)
        button2.setOnClickListener {
            if (allPermissionsGrantedFlag == 1) {
                val intent = Intent(this, ActivityQRCodeFromCamera::class.java)
                startActivityForResult(intent, PROCESS_QRCODE_REQUEST)
            }
        }
    }

    // For this method only required a certain minsdk then dunnid specify in manifest
    @RequiresApi(Build.VERSION_CODES.M)
    private fun allPermissionsEnabled(): Boolean {
        return permissionList.none {
            // it means items in checkSelfPermission
            checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupMultiplePermissions() {
        val remainingPermissions = permissionList.filter {
            checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED
        }
        requestPermissions(remainingPermissions.toTypedArray(), 101)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissionList: Array<out
    String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissionList, grantResults)
        if (requestCode == 101) {
            if (grantResults.any { it != PackageManager.PERMISSION_GRANTED }) {
                @TargetApi(Build.VERSION_CODES.M)
                if (permissionList.any { shouldShowRequestPermissionRationale(it) }) {
                    AlertDialog.Builder(this)
                            .setMessage("Press Permissions to Decide Permission Again")
                            .setPositiveButton("Permissions") { dialog, which -> setupMultiplePermissions() }
                            .setNegativeButton("Cancel") { dialog, which -> dialog.dismiss() }
                            .create()
                            .show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // AFter qrcode is detected
        if (requestCode == PROCESS_QRCODE_REQUEST && resultCode == RESULT_OK) run {
            val scannedText = data?.getStringExtra("SCANNEDTEXT")
            Log.d("MainActivity", "Scanned text string = " + scannedText)

            // Set to payment page
            val intent = Intent(this, ActivityPayment::class.java)
            intent.putExtra("FOODITEM", scannedText)
            startActivity(intent)
        }
    }
}
