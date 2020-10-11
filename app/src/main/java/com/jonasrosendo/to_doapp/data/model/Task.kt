package com.jonasrosendo.to_doapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jonasrosendo.to_doapp.data.model.Priority
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tasks")
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var title: String,
    var priority: Priority,
    var description: String
) : Parcelable