package com.example.app1.roomDb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface Room_1Dao {
    @Upsert
    suspend fun upsertMessage(message: com.example.app1.roomDb.Lazycolumn_1)
    @Delete
    suspend fun deleteMessage(message: com.example.app1.roomDb.Lazycolumn_1)
    @Query("SELECT * FROM lazy_message")
    fun getAllMessages(): Flow<List<Lazycolumn_1>>
}