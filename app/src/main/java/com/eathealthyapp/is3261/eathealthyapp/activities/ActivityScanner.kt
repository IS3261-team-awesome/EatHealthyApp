package com.eathealthyapp.is3261.eathealthyapp.activities

import android.content.Context
import android.content.Intent
import android.os.*
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
import com.eathealthyapp.is3261.eathealthyapp.R
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import java.io.IOException


/**
 * A simple [Fragment] subclass.
 */
class ActivityScanner : AppCompatActivity() {

    lateinit var barcodeDetector: BarcodeDetector
    lateinit var cameraSource: CameraSource
    lateinit var cameraView: SurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        startDetection()
    }

    fun startDetection() {
        cameraView = findViewById<SurfaceView>(R.id.surfaceView)
        barcodeDetector = BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build()
        cameraSource = CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build()

        cameraView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder?) {
                try {
                    cameraSource.start(cameraView.holder)
                } catch (ie: IOException) {
                    Log.e("CAMERA SOURCE", ie.message)
                }
            }
            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int,
                                        height: Int) {
            }
            override fun surfaceDestroyed(holder: SurfaceHolder?) {
                cameraSource.stop()
            }
        })

        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            lateinit var scannedText: String
            override fun release() {
                goToPaymentPage(scannedText)
            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>?) {
                val barcodes = detections?.detectedItems
                if (barcodes?.size() != 0) {
                    // Get scanned text from qr code
                    scannedText = barcodes?.valueAt(0)?.displayValue!!

                    // Vibrate and release barcode dectector
                    val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    if (Build.VERSION.SDK_INT >= 26) {
                        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        vibrator.vibrate(200);
                    }
                    barcodeDetector.release()
                }
            }
        })
    }

    // TODO: Go to selection page instead of payment page
    fun goToPaymentPage(scannedText: String?){
        val paymentIntent = Intent(this, ActivityPayment::class.java)
        paymentIntent.putExtra("SCANNEDTEXT", scannedText)
        startActivity(paymentIntent)
        finish()
    }
}
