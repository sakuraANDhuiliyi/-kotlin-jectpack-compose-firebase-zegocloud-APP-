package com.example.app1.roomDb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lazy_message")
data class Lazycolumn_1(
    val author: String,
    val body: String,
    val imageURL: String,
    val fuiteName: String,
    val descriptionfuite:String,
    @PrimaryKey(autoGenerate = true)
    val Id : Int = 0

)