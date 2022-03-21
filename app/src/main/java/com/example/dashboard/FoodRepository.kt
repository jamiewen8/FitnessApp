package com.example.dashboard

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*


@OptIn(InternalCoroutinesApi::class)
class FoodRepository(application: Application) {

    val allFoods: LiveData<List<Food>>?

    val searchResults = MutableLiveData<List<Food>>()
    private var foodDao: FoodDao?
    private val coroutineScope = CoroutineScope(Dispatchers.Main)




    init {
        val db: FoodDatabase? =
            FoodDatabase.getDatabase(application)
        foodDao = db?.foodDao()
        allFoods = foodDao?.getAllFoods()
    }

    fun insertFood(newfood: Food) {
        coroutineScope.launch(Dispatchers.IO) {
            asyncInsert(newfood)

        }
    }

    private suspend fun asyncInsert(food: Food) {
        foodDao?.insertFood(food)
    }

    fun deleteFood(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            asyncDelete(name)
        }
    }

    private suspend fun asyncDelete(name: String) {
        foodDao?.deleteFood(name)
    }



    fun findFood (name: String) {

        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(name).await()
        }
    }

    private suspend fun asyncFind(name: String): Deferred<List<Food>?> =

        coroutineScope.async(Dispatchers.IO) {
            return@async foodDao?.findFood(name)
        }
}