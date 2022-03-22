package com.example.dashboard.logic.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.dashboard.data.models.Food

@Dao
interface FoodDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun addFood(food: Food)

    @Update
    fun updateFood(food: Food)

    @Query("DELETE FROM food_table WHERE food_name = :foodMember")
    fun deleteFood(foodMember: String)

    @Query("SELECT protein AND carbs AND fat FROM food_table WHERE id = :barcode")
    fun getMacros(barcode: Int)

    @Query("SELECT * FROM food_table ORDER BY id DESC")
    fun getAllFoods(): LiveData<List<Food>>

    @Query("DELETE FROM food_table")
    fun deleteAll()


}