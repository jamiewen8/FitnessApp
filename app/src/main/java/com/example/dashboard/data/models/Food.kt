package com.example.dashboard.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "food_table")
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val food_name: String,
    val protein: String,    //todo need to change this to integer because the strings are for testing
    val fat: String,
    val carbs: String,
    val imageId: Int,
    val barcode: Int)
    : Parcelable