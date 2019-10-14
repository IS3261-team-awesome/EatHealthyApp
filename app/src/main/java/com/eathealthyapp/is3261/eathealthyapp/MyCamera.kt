package com.eathealthyapp.is3261.eathealthyapp

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by franc on 14/10/2019.
 */
class MyCamera(private val currentActivity: Activity) {
    val IMAGE_FOLDER_NAME = "UserPictures"
    val TAKE_PHOTO_REQUEST = 1
    var pictureFile: File? = null
        private set

    fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(currentActivity.packageManager) == null) {
            println("Problem loading camera")
        } else {
            try {
                pictureFile = createEmptyFile()
            } catch (ex: IOException) {
                println("Error occurred while creating the an empty file for image")
            }

            // Continue only if the empty file was successfully created
            if (pictureFile != null) {
                val photoURI = FileProvider.getUriForFile(currentActivity,
                        "com.example.android.fileprovider.app",
                        pictureFile)

                // Add extra instructions to intent to store the image output(EXTRA_OUTPUT) into photoURI of the empty file created
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                currentActivity.startActivityForResult(takePictureIntent, TAKE_PHOTO_REQUEST)
            }
        }
    }

    @Throws(IOException::class)
    fun createEmptyFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_$timeStamp"
        val storageDir = currentActivity.getExternalFilesDir(IMAGE_FOLDER_NAME)

        //This will may not give a unique name
        //File imageFile = new File(storageDir, imageFileName + ".jpg");

        //But this will give a unique file name by adding -(some number) to end of file name
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    }


}