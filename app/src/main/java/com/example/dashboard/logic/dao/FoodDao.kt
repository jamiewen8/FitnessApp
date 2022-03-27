package com.example.dashboard.logic.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.dashboard.data.models.Food

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFood(food: Food)

    @Update
    suspend fun updateFood(food: Food)

    @Delete
    suspend fun deleteFood(food: Food)

    @Query("SELECT * FROM food_table ORDER BY id DESC")
    fun getAllFoods(): LiveData<List<Food>>

    @Query("DELETE FROM food_table")
    suspend fun deleteAll()



}