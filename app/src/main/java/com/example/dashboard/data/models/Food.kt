package com.example.dashboard.data.models

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//@Parcelize
@Entity(tableName = "food_table")
class Food {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "Barcode")
    var id: Int = 0
    @ColumnInfo(name = "food_name")
    var food_name: String? = null

    //val measurement: String,
    var protein: String? = null
    var fat: String? = null
    var carbs: String? = null
    var imageId: Int = 0


    constructor() {}

    constructor(
        id: Int,
        food_name: String,
        protein: String,
        fat: String,
        carbs: String,
        imageId: Int
    ) {
        this.food_name = food_name
        this.protein = protein
        this.fat = fat
        this.carbs = carbs
        this.imageId = imageId
    }

    constructor(food_name: String, protein: String, fat: String, carbs: String, imageId: Int) {
        this.food_name = food_name
        this.protein = protein
        this.fat = fat
        this.carbs = carbs
        this.imageId = imageId
    }


}