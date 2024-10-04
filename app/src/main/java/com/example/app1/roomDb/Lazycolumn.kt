package com.example.app1.roomDb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class Lazycolumn(
    val author: String,
    val body: String,
    @PrimaryKey(autoGenerate = true)
    val Id : Int = 0

)
