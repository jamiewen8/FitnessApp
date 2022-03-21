package com.example.dashboard.ui.fragments.createfood


import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dashboard.R
import com.example.dashboard.data.models.Food
import com.example.dashboard.ui.viewmodels.FoodViewModel
import kotlinx.android.synthetic.main.fragment_create_food_item.*
import java.util.*


class CreateFoodItem : Fragment(R.layout.fragment_create_food_item) {



    private var drawableSelected = 0
    private var food_name = ""
    private var protein = 0
    private var fat = 0
    private var carbs = 0
    private var measurement = 0


    private lateinit var foodViewModel: FoodViewModel


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        foodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)

        //Add food to database
        btn_confirm.setOnClickListener {
            addFoodToDB()
        }

        //Selected and image to put into our list
        drawableSelected()

    }

    private fun addFoodToDB() {


        protein()
        fat()
        carbs()
        measurement()



        if ( drawableSelected == 0) {
            val food = Food(0, "measurement","protein","fat","carbs", drawableSelected) //todo add foodname here

            //add the food if all the fields are filled
            foodViewModel.addFood(food)
            Toast.makeText(context, "Food selected successfully!", Toast.LENGTH_SHORT).show()

            //navigate back to our home fragment
            findNavController().navigate(R.id.action_createFoodItem_to_foodList)
        } else {
            Toast.makeText(context, "Please select the food you want to add", Toast.LENGTH_SHORT).show()
        }
    }

    private fun drawableSelected() {
        something1.setOnClickListener {
            something1.isSelected = !something1.isSelected
            drawableSelected = R.drawable.ic_tea

            //de-select the other options when we pick an image
            something2.isSelected = false
            something3.isSelected = false
            something4.isSelected = false
            something5.isSelected = false
        }

        something2.setOnClickListener {
            something2.isSelected = !something2.isSelected
            drawableSelected = R.drawable.ic_tea

            //de-select the other options when we pick an image
            something1.isSelected = false
            something3.isSelected = false
            something4.isSelected = false
            something5.isSelected = false
        }

        something3.setOnClickListener {
            something3.isSelected = !something3.isSelected
            drawableSelected = R.drawable.ic_tea

            //de-select the other options when we pick an image
            something2.isSelected = false
            something1.isSelected = false
            something4.isSelected = false
            something5.isSelected = false
        }

        something4.setOnClickListener {
            something4.isSelected = !something4.isSelected
            drawableSelected = R.drawable.ic_tea

            //de-select the other options when we pick an image
            something2.isSelected = false
            something3.isSelected = false
            something1.isSelected = false
            something5.isSelected = false
        }

        something5.setOnClickListener {
            something5.isSelected = !something5.isSelected
            drawableSelected = R.drawable.ic_tea

            //de-select the other options when we pick an image
            something2.isSelected = false
            something3.isSelected = false
            something4.isSelected = false
            something1.isSelected = false
        }

    }

    private fun protein() {
        something1.setOnClickListener {
            protein = 5
        }

        something2.setOnClickListener {
            protein = 5
        }

        something3.setOnClickListener {
            protein = 5
        }

        something4.setOnClickListener {
            protein = 5
        }

        something5.setOnClickListener {
            protein = 5
        }

    }
    private fun fat() {
        something1.setOnClickListener {
            fat = 5
        }

        something2.setOnClickListener {
            fat = 5
        }

        something3.setOnClickListener {
            fat = 5
        }

        something4.setOnClickListener {
            fat = 5
        }

        something5.setOnClickListener {
            fat = 5
        }

    }
    private fun carbs() {
        something1.setOnClickListener {
            carbs = 5
        }

        something2.setOnClickListener {
            carbs = 5
        }

        something3.setOnClickListener {
            carbs = 5
        }

        something4.setOnClickListener {
            carbs = 5
        }

        something5.setOnClickListener {
            carbs = 5
        }

    }
    private fun measurement() {
        something1.setOnClickListener {
            measurement = 5
        }

        something2.setOnClickListener {
            measurement = 5
        }

        something3.setOnClickListener {
            measurement = 5
        }

        something4.setOnClickListener {
            measurement = 5
        }

        something5.setOnClickListener {
            measurement = 5
        }

    }



}