package com.inspirecoding.roombasics.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "ToDo")
data class ToDo (
    @PrimaryKey(autoGenerate = true)
    var toDoId: Int = 0,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "dueDate")
    var dueDate: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "priority")
    var priority: String
) : Parcelable