package com.example.app1.roomDb.viewModel

import com.example.app1.roomDb.Lazycolumn
import com.example.app1.roomDb.LazycolumnDatabase

class Repository(private val db: LazycolumnDatabase) {


    suspend fun upsertMessage(message: Lazycolumn){
        db.dao.upsertMessage(message)
    }

    suspend fun deleteMessage(message: Lazycolumn){
        db.dao.deleteMessage(message)
    }

    fun getAllMessages()=
        db.dao.getAllMessages()

}