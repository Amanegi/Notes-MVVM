package com.amannegi.notes.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) var uid: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "body") val body: String,
    @ColumnInfo(name = "owner") val owner: String,
    @ColumnInfo(name = "timestamp") val timestamp: String
) : Parcelable
// room automatically knows that uid is autogenerate and will reject the uid parameter passed during object creation
// added parcelable to pass this class as a custom data type in safe args (navigation component)