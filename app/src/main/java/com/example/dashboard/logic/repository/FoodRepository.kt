package com.example.dashboard.logic.repository

import androidx.lifecycle.LiveData
import com.example.dashboard.data.models.Food
import com.example.dashboard.logic.dao.FoodDao

class FoodRepository(private val foodDao: FoodDao) {
    val getAllFoods: LiveData<List<Food>> = foodDao.getAllFoods()

    suspend fun addFood(food: Food) {
        foodDao.addFood(food)
    }

    suspend fun updateFood(food: Food) {
        foodDao.updateFood(food)
    }

    suspend fun deleteFood(food: Food) {
        foodDao.deleteFood(food)
    }

    suspend fun deleteAllFoods() {
        foodDao.deleteAll()
    }



}