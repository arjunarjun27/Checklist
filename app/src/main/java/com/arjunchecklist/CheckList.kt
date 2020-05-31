package com.arjunchecklist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "checklist_table")
data class CheckList(
    var title: String,
    var date: String,
    var time: String

){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}