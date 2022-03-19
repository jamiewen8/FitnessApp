package com.example.dashboard

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "foods")

class Food {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "Barcode")
    var id: Int = 0

    @ColumnInfo(name = "foodName")
    var foodName: String? = null
    var proteinContent: Int? = null
    var fatContent: Int? = null
    var carbsContent: Int? = null
    var calorieContent: Int? = null


    constructor() {}



}
