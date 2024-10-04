package com.example.app1.roomDb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Lazycolumn::class],
    version = 1,
    exportSchema = false
)

abstract class LazycolumnDatabase : RoomDatabase() {
    abstract val dao : RoomDao
}