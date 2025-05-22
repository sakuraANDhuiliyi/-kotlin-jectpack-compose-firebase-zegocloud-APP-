package com.example.app1

import android.app.Application
import androidx.room.Room
import com.example.app1.roomDb.TodoDatabase
import com.zegocloud.zimkit.services.ZIMKit
import com.zegocloud.zimkit.services.ZIMKitConfig

class MainApplication : Application() {

    companion object {
        lateinit var todoDatabase: TodoDatabase
    }

    override fun onCreate() {
        super.onCreate()
        todoDatabase = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,
            TodoDatabase.NAME
        ).build()

        // Initialize ZIMKit SDK
        val appID = 678584385L // Consistent App ID
        val appSign = "26022f0371369fd9f38e05f3550990990130877601596773211d131c1622dc7c" // Updated App Sign from subtask description
        ZIMKit.initWith(this, appID, appSign, ZIMKitConfig()) // `this` refers to the Application instance
        ZIMKit.initNotifications()
    }

}