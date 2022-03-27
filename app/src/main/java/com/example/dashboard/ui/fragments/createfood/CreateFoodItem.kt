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


class CreateFoodItem : Fragment(R.layout.fragment_create_food_item) {



    private var drawableSelected = 0
    private var food_name = ""
    private var protein = ""
    private var fat = ""
    private var carbs = ""
    private var barcode = 0



    private lateinit var foodViewModel: FoodViewModel


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        foodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)

        //Add food to database
        btn_confirm.setOnClickListener {
            addFoodToRecycler()
        }

        //Selected and image to put into our list
        drawableSelected()

    }

    private fun addFoodToRecycler() {

        drawableSelected()

        if (!( drawableSelected == 0)) {
            val food = Food(0, food_name,protein,fat,carbs, drawableSelected, barcode)

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


            barcode = 1

            food_name = "Coco pops"
            protein = "1.9"
            fat = "0.6"
            carbs = "25"
            //here i can hardcode the data for the recycler view
            //de-select the other options when we pick an image
            something2.isSelected = false
            something3.isSelected = false
            something4.isSelected = false
            something5.isSelected = false
        }

        something2.setOnClickListener {
            something2.isSelected = !something2.isSelected
            drawableSelected = R.drawable.ic_tea

            barcode = 2

            food_name = "Frosties"
            protein = "1.6"
            fat = "0.2"
            carbs = "30"

            //de-select the other options when we pick an image
            something1.isSelected = false
            something3.isSelected = false
            something4.isSelected = false
            something5.isSelected = false
        }

        something3.setOnClickListener {
            something3.isSelected = !something3.isSelected
            drawableSelected = R.drawable.ic_tea

            barcode = 3

            food_name = "Crunchy nut"
            protein = "2.5"
            fat = "0.3"
            carbs = "35"

            //de-select the other options when we pick an image
            something2.isSelected = false
            something1.isSelected = false
            something4.isSelected = false
            something5.isSelected = false
        }

        something4.setOnClickListener {
            something4.isSelected = !something4.isSelected
            drawableSelected = R.drawable.ic_tea

            barcode = 4

            food_name = "Lucky charms"
            protein = "1.4"
            fat = "0.5"
            carbs = "45"

            //de-select the other options when we pick an image
            something2.isSelected = false
            something3.isSelected = false
            something1.isSelected = false
            something5.isSelected = false
        }

        something5.setOnClickListener {
            something5.isSelected = !something5.isSelected
            drawableSelected = R.drawable.ic_tea

            barcode = 5

            food_name = "Weetabix"
            protein = "4.5"
            fat = "0.8"
            carbs = "26"

            //de-select the other options when we pick an image
            something2.isSelected = false
            something3.isSelected = false
            something4.isSelected = false
            something1.isSelected = false
        }

    }





}