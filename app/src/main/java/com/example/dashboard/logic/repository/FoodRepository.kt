package com.example.dashboard.logic.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dashboard.data.database.FoodDatabase
import com.example.dashboard.data.models.Food
import com.example.dashboard.logic.dao.FoodDao
import kotlinx.coroutines.*


@OptIn(InternalCoroutinesApi::class)
class FoodRepository(application: Application) {

    val allFoods: LiveData<List<Food>>?

    val searchResults = MutableLiveData<List<Food>>() //asynchronous search
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
        foodDao?.addFood(food)
    }

    fun deleteFood(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            asyncDelete(name)
        }
    }

    private suspend fun asyncDelete(name: String) {
        foodDao?.deleteFood(name)
    }



    fun findFood (id: Int) {

        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(id).await()
        }
    }


    private suspend fun asyncFind(id: Int): Deferred<List<Food>?> =

        coroutineScope.async(Dispatchers.IO) {
            return@async foodDao?.getMacros(id)
        }
}


