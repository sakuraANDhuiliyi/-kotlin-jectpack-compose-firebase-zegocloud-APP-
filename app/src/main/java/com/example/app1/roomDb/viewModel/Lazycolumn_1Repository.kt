package com.example.app1.roomDb.viewModel

import androidx.room.RoomDatabase
import com.example.app1.roomDb.Lazycolumn_1
import com.example.app1.roomDb.Lazycolumn_1Database

class Lazycolumn_1Repository(private val db: Lazycolumn_1Database) {


    suspend fun upsertMessage(message: Lazycolumn_1){
        db.dao.upsertMessage(message)
    }

    suspend fun deleteMessage(message: Lazycolumn_1){
        db.dao.deleteMessage(message)
    }

    fun getAllMessages()=
        db.dao.getAllMessages()

}