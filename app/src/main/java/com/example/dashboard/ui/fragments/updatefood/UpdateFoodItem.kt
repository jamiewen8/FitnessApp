package com.example.dashboard.ui.fragments.updatefood

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dashboard.R
import com.example.dashboard.data.models.Food
import com.example.dashboard.ui.viewmodels.FoodViewModel
import kotlinx.android.synthetic.main.fragment_create_food_item.*
import kotlinx.android.synthetic.main.fragment_updatefooditem.*


class UpdateFoodItem : Fragment(R.layout.fragment_updatefooditem) {

    private val args by navArgs<UpdateFoodItemArgs>()


    private var drawableSelected = 0
    private var food_name = ""
    private var protein = ""
    private var fat = ""
    private var carbs = ""
    private var barcode = 0
    //todo make the breakfast lunch and dinner buttons so it can display what it is for
    //todo make sure to get the update working as well
    //todo have a search function
    //todo have a slide to delete function
    //try to then link everything to the db


    private lateinit var foodViewModel: FoodViewModel


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        foodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)

        proteinDisplayUpdate.setText(args.selectedFood.protein)
        fatDisplayUpdate.setText(args.selectedFood.fat)
        carbsDisplayUpdate.setText(args.selectedFood.carbs)


        //Add food to database
        btn_confirm_update.setOnClickListener {
            updateFoodToRecycler()
        }

        //Selected and image to put into our list
        drawableSelected()

    }

    private fun updateFoodToRecycler() {

        drawableSelected()

        if (!( drawableSelected == 0)) {
            val food = Food(args.selectedFood.id, food_name,protein,fat,carbs, drawableSelected, barcode)

            //add the food if all the fields are filled
            foodViewModel.updateFood(food)
            Toast.makeText(context, "Food updated successfully!", Toast.LENGTH_SHORT).show()

            //navigate back to our home fragment
            findNavController().navigate(R.id.action_updateFoodItem_to_foodList)
        } else {
            Toast.makeText(context, "Please select the food you want to add", Toast.LENGTH_SHORT).show()
        }
    }

    private fun drawableSelected() {
        something1update.setOnClickListener {
            something1update.isSelected = !something1update.isSelected
            drawableSelected = R.drawable.coco_pops

            proteinDisplayUpdate.text = "1.9"
            fatDisplayUpdate.text = "0.6"
            carbsDisplayUpdate.text = "25"


            barcode = 1

            food_name = "Coco pops"
            protein = "1.9"
            fat = "0.6"
            carbs = "25"
            //here i can hardcode the data for the recycler view try to link this to the db

            something1update.alpha = 1f

            //de-select the other options when we pick an image
            something2update.isSelected = false
            something2update.alpha = 0.5f
            something3update.isSelected = false
            something3update.alpha = 0.5f
            something4update.isSelected = false
            something4update.alpha = 0.5f
            something5update.isSelected = false
            something5update.alpha = 0.5f
        }

        something2update.setOnClickListener {
            something2update.isSelected = !something2update.isSelected
            drawableSelected = R.drawable.frosties

            proteinDisplayUpdate.text = "1.6"
            fatDisplayUpdate.text = "0.2"
            carbsDisplayUpdate.text = "30"

            barcode = 2

            food_name = "Frosties"
            protein = "1.6"
            fat = "0.2"
            carbs = "30"

            something2update.alpha = 1f

            //de-select the other options when we pick an image
            something1update.isSelected = false
            something1update.alpha = 0.5f
            something3update.isSelected = false
            something3update.alpha = 0.5f
            something4update.isSelected = false
            something4update.alpha = 0.5f
            something5update.isSelected = false
            something5update.alpha = 0.5f
        }

        something3update.setOnClickListener {
            something3update.isSelected = !something3update.isSelected
            drawableSelected = R.drawable.crunchy_nut

            proteinDisplayUpdate.text = "2.5"
            fatDisplayUpdate.text = "0.3"
            carbsDisplayUpdate.text = "35"

            barcode = 3

            food_name = "Crunchy nut"
            protein = "2.5"
            fat = "0.3"
            carbs = "35"

            something3update.alpha = 1f

            //de-select the other options when we pick an image
            something2update.isSelected = false
            something2update.alpha = 0.5f
            something1update.isSelected = false
            something1update.alpha = 0.5f
            something4update.isSelected = false
            something4update.alpha = 0.5f
            something5update.isSelected = false
            something5update.alpha = 0.5f
        }

        something4update.setOnClickListener {
            something4update.isSelected = !something4update.isSelected
            drawableSelected = R.drawable.lucky_charms

            proteinDisplayUpdate.text = "1.4"
            fatDisplayUpdate.text = "0.5"
            carbsDisplayUpdate.text = "45"

            barcode = 4

            food_name = "Lucky charms"
            protein = "1.4"
            fat = "0.5"
            carbs = "45"

            something4update.alpha = 1f

            //de-select the other options when we pick an image
            something2update.isSelected = false
            something2update.alpha = 0.5f
            something3update.isSelected = false
            something3update.alpha = 0.5f
            something1update.isSelected = false
            something1update.alpha = 0.5f
            something5update.isSelected = false
            something5update.alpha = 0.5f
        }

        something5update.setOnClickListener {
            something5update.isSelected = !something5update.isSelected
            drawableSelected = R.drawable.weetabix

            proteinDisplayUpdate.text = "4.5"
            fatDisplayUpdate.text = "0.8"
            carbsDisplayUpdate.text = "26"

            barcode = 5

            food_name = "Weetabix"
            protein = "4.5"
            fat = "0.8"
            carbs = "26"

            something5update.alpha = 1f

            //de-select the other options when we pick an image
            something2update.isSelected = false
            something2update.alpha = 0.5f
            something3update.isSelected = false
            something3update.alpha = 0.5f
            something4update.isSelected = false
            something4update.alpha = 0.5f
            something1update.isSelected = false
            something1update.alpha = 0.5f
        }

    }






}