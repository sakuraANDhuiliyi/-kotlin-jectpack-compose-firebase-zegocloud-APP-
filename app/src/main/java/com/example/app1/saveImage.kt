package com.example.app1

import android.content.Context
import android.net.Uri
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileOutputStream


fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
    return try {
        val fileName = "${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName)
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
fun saveImagePath(context: Context, imagePath: String) {
    val sharedPreferences = context.getSharedPreferences("image_pref", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("image_path", imagePath)
    editor.apply()
}
fun getImagePath(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("image_pref", Context.MODE_PRIVATE)
    return sharedPreferences.getString("image_path", "https://truth.bahamut.com.tw/s01/201804/b34f037ab8301d4cd1331f686405b97a.JPG")
}


fun saveUserName(context: Context, userName: String) {
    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("user_name", userName)
    editor.apply()
}

fun loadUserName(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString("user_name", "默认用户")
}
