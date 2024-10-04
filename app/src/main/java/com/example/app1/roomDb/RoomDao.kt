package com.example.app1.roomDb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface RoomDao {
    @Upsert
    suspend fun upsertMessage(message: com.example.app1.roomDb.Lazycolumn)
    @Delete
    suspend fun deleteMessage(message: com.example.app1.roomDb.Lazycolumn)
    @Query("SELECT * FROM message")
    fun getAllMessages(): Flow<List<Lazycolumn>>
}