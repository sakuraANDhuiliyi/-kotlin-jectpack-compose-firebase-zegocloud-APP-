package com.example.app1.roomDb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Lazycolumn_1::class],
    version = 1,
    exportSchema = false
)

abstract class Lazycolumn_1Database : RoomDatabase() {
    abstract val dao : Room_1Dao
}