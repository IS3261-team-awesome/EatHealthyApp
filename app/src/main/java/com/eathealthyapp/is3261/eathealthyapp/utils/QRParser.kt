package com.eathealthyapp.is3261.eathealthyapp.utils

/**
 * Created by franc on 10/11/2019.
 */
class QRParser {
    companion object {
        fun getFoodName(scannedText: String): String {
            val scannedTextParts = scannedText.split("-", limit = 3)
            return scannedTextParts[0]
        }

        fun getFoodStall(scannedText: String): String {
            val scannedTextParts = scannedText.split("-", limit = 3)
            return scannedTextParts[1]
        }

        fun getBasePrice(scannedText: String): Float {
            val scannedTextParts = scannedText.split("-", limit = 3)
            return scannedTextParts[2].toFloat()
        }
    }
}