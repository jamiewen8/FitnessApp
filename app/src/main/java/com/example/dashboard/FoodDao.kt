package com.example.dashboard

import androidx.room.Dao
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FoodDao {

    @Insert
    fun insertFood(food: Food)


    @Query("SELECT * FROM foods WHERE foodName = :name")
    fun findFood(name: String): List<Food>

    @Query("DELETE FROM foods WHERE foodName = :name")
    fun deleteFood(name: String)

    @Query("SELECT * FROM foods")
    fun getAllFoods(): LiveData<List<Food>>
}