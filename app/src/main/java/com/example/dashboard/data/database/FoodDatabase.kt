package com.example.dashboard.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dashboard.data.models.Food
import com.example.dashboard.logic.dao.FoodDao
import kotlinx.coroutines.InternalCoroutinesApi

@Database(entities = [Food::class], version = 1)
abstract class FoodDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao

    companion object {

        private var INSTANCE: FoodDatabase? = null

        @InternalCoroutinesApi
        internal fun getDatabase(context: Context): FoodDatabase? {
            if (INSTANCE == null) {
                kotlinx.coroutines.internal.synchronized(FoodDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE =
                            Room.databaseBuilder<FoodDatabase>(
                                context.applicationContext,
                                FoodDatabase::class.java,
                                "food_database"
                            ).build()
                    }
                }
            }
            return INSTANCE

        }
    }
}