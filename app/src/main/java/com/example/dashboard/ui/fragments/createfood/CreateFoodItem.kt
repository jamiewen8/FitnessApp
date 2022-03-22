package com.example.dashboard.ui.fragments.createfood


import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dashboard.R
import com.example.dashboard.data.models.Food
import com.example.dashboard.ui.viewmodels.FoodViewModel
import kotlinx.android.synthetic.main.fragment_create_food_item.*
import java.util.*


class CreateFoodItem : Fragment(R.layout.fragment_create_food_item) {


    private val viewModel: FoodViewModel by viewModels()






    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //Add food to database
        val name = arrayListOf<String>("Coco pops", "Frosties","Crunchy nut","Lucky charms")
        val protein = arrayListOf<String>("1.9", "1.6", "2.5", "1.4")
        val fat = arrayListOf<String>("0.6", "0.2", "0.3", "0.5")
        val carbs = arrayListOf<String>("25", "30", "35", "45")
        val drawableSelected = arrayListOf<Int>(1, 2, 3, 4)
        for(i in 0..name.size-1){
            val food = Food(i, name[i], protein[i], fat[i], carbs[i], drawableSelected[i])
            viewModel.insertFood(food)
        }






        //Selected and image to put into our list
        //drawableSelected()

    }

    //todo get rid of the measurement thing





}