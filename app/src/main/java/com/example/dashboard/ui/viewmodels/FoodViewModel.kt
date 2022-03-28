package com.example.dashboard.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dashboard.data.models.Food
import com.example.dashboard.data.database.FoodDatabase
import com.example.dashboard.logic.repository.FoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FoodRepository
    val getAllFoods: LiveData<List<Food>>
    


    init {
        val foodDao= FoodDatabase.getDatabase(application).foodDao()
        repository = FoodRepository(foodDao)

        getAllFoods = repository.getAllFoods
    }

    fun addFood(food: Food) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFood(food)
        }
    }

    fun updateFood(food: Food) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFood(food)
        }
    }

    fun deleteFood(food: Food) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFood(food)
        }
    }

    fun deleteAllFoods() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllFoods()
        }
    }





}