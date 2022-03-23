package com.example.dashboard.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dashboard.data.models.Food
import com.example.dashboard.data.database.FoodDatabase
import com.example.dashboard.logic.repository.FoodRepository
import com.google.zxing.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class FoodViewModel(application: Application): AndroidViewModel(application){

    private val repository: FoodRepository = FoodRepository(application)
    private val allFood: LiveData<List<Food>>?
    private val searchResults: MutableLiveData<List<Food>>



    init {
        allFood = repository.allFoods
        searchResults = repository.searchResults
    }


    fun insertFood(food: Food)
    {
        repository.insertFood(food)
    }


    fun deleteFood(name: String)
    {
        repository.deleteFood(name)
    }

    fun findFood(barcode: Int) {
        repository.findFood(barcode)
    }


    fun getSearchResults(): MutableLiveData<List<Food>> {
        return searchResults
    }



}