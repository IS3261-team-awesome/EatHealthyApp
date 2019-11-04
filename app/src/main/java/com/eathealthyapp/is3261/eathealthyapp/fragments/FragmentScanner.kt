package com.eathealthyapp.is3261.eathealthyapp.fragments

import android.content.Context
import android.os.*
import android.support.v4.app.Fragment
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
class FragmentScanner : Fragment() {

    lateinit var dataReceiver: ReceiverOfScanner
    lateinit var barcodeDetector: BarcodeDetector
    lateinit var cameraSource: CameraSource
    lateinit var cameraView: SurfaceView

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        dataReceiver = context as ReceiverOfScanner
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_scanner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraView = view.findViewById<SurfaceView>(R.id.surfaceView)
        barcodeDetector = BarcodeDetector.Builder(context)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build()
        cameraSource = CameraSource.Builder(context, barcodeDetector)
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
    }

    // Method used by main activity
    fun startDetection() {
        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>?) {
                val barcodes = detections?.detectedItems
                if (barcodes?.size() != 0) {
                    // Get scanned text from qr code
                    val scannedText: String? = barcodes?.valueAt(0)?.displayValue;

                    // Vibrate and release barcode dectector
                    val vibrator = context!!.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    if (Build.VERSION.SDK_INT >= 26) {
                        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        vibrator.vibrate(200);
                    }
                    barcodeDetector.release()

                    // Update changes in main activity
                    activity!!.runOnUiThread(Runnable {passDataToMainActivity(scannedText!!)})
                }
            }
        })
    }

    // Method used by main activity
    fun stopDetection() {
        barcodeDetector.release()
    }

    fun passDataToMainActivity(foodText: String) {
        dataReceiver.onReceiveDataFromScanner(foodText)
    }

    interface ReceiverOfScanner {
        fun onReceiveDataFromScanner(data: String)
    }
}
