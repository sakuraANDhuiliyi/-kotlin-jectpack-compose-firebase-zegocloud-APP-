package com.example.app1

import android.content.Context
import android.net.Uri
import android.util.Log

import com.google.firebase.firestore.FirebaseFirestore
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
    val currentUserName = getLoggedInUsername(context)
    val sharedPreferences = context.getSharedPreferences("image_pref", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    val db = FirebaseFirestore.getInstance()
    val userDocRef = db.collection("users").document(currentUserName)
    editor.putString("image_path", imagePath)
    editor.apply()
    userDocRef.update("imageUrl",imagePath )
        .addOnSuccessListener {
            Log.d("Firebase", "Image URL successfully updated!")
        }
        .addOnFailureListener { e ->
            Log.w("Firebase", "Error updating image URL", e)
        }.addOnFailureListener { e ->
    Log.w("Firebase", "Error fetching user document", e)
}

}
//无法获取图片url，感觉是因为初始化的问题，，，哦，可以获取，就是延迟更新
fun getImagePath(context: Context): String {
    val currentUserName = getLoggedInUsername(context)
    val db = FirebaseFirestore.getInstance()
    val userDocRef = db.collection("users").document(currentUserName)
    var imageURL = "https://c-ssl.duitang.com/uploads/item/201803/19/20180319200326_3HvLA.jpeg"
    userDocRef.get().addOnSuccessListener { document ->
       imageURL = if (document.exists()) {
            // 获取 favoriteVideos 列表 (DocumentReference 类型)
            (document.get("imageUrl") as? String).toString()
        } else {
            "https://c-ssl.duitang.com/uploads/item/201803/19/20180319200326_3HvLA.jpeg"
        }
    }.addOnFailureListener { e ->
        Log.w("Firebase", "Error fetching user document", e)
    }
    return imageURL
}


fun saveUserName(context: Context, userName: String) {
    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("user_name", userName)
    editor.apply()
}

