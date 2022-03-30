package com.example.dashboard.ui.fragments.createfood


import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dashboard.R
import com.example.dashboard.data.models.Food
import com.example.dashboard.databinding.FragmentCreateFoodItemBinding
import com.example.dashboard.ui.viewmodels.FoodViewModel
import kotlinx.android.synthetic.main.fragment_create_food_item.*
import kotlinx.android.synthetic.main.recycler_food_item.*


class CreateFoodItem : Fragment() {


    private var _binding: FragmentCreateFoodItemBinding? = null
    private val binding get() = _binding!!


    private var drawableSelected = 0
    private var food_name = ""
    private var protein = ""
    private var fat = ""
    private var carbs = ""
    private var barcode = 0
    private var foodtimeadapter = ""



    private lateinit var foodViewModel: FoodViewModel


    override fun onResume() {
        super.onResume()
        //val options = resources.getStringArray(R.array.foodtime)
        val options = arrayOf("Breakfast", "Lunch", "Dinner")
        val arrayAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.select_dialog_item,options)

        //val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, options)
        autoCompleteTextView.setAdapter(arrayAdapter)

        //foodtimeadapter = binding.autoCompleteTextView.text.toString()

        autoCompleteTextView.setOnItemClickListener{parent, view, position, id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            Toast.makeText(requireContext(), "You Selected " + selectedItem, Toast.LENGTH_SHORT).show()
            foodtimeadapter = selectedItem
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        _binding = FragmentCreateFoodItemBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


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

        if (!( drawableSelected == 0) || (foodtimeadapter.isNotEmpty())) {
            val food = Food(0, food_name,protein,fat,carbs, drawableSelected, barcode, foodtimeadapter)



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
            drawableSelected = R.drawable.coco_pops

            proteinDisplay.text = "1.9"
            fatDisplay.text = "0.6"
            carbsDisplay.text = "25"


            barcode = 1

            food_name = "Coco pops"
            protein = "1.9"
            fat = "0.6"
            carbs = "25"
            //here i can hardcode the data for the recycler view try to link this to the db

            something1.alpha = 1f

            //de-select the other options when we pick an image
            something2.isSelected = false
            something2.alpha = 0.5f
            something3.isSelected = false
            something3.alpha = 0.5f
            something4.isSelected = false
            something4.alpha = 0.5f
            something5.isSelected = false
            something5.alpha = 0.5f
        }

        something2.setOnClickListener {
            something2.isSelected = !something2.isSelected
            drawableSelected = R.drawable.frosties

            proteinDisplay.text = "1.6"
            fatDisplay.text = "0.2"
            carbsDisplay.text = "30"

            barcode = 2

            food_name = "Frosties"
            protein = "1.6"
            fat = "0.2"
            carbs = "30"

            something2.alpha = 1f

            //de-select the other options when we pick an image
            something1.isSelected = false
            something1.alpha = 0.5f
            something3.isSelected = false
            something3.alpha = 0.5f
            something4.isSelected = false
            something4.alpha = 0.5f
            something5.isSelected = false
            something5.alpha = 0.5f
        }

        something3.setOnClickListener {
            something3.isSelected = !something3.isSelected
            drawableSelected = R.drawable.crunchy_nut

            proteinDisplay.text = "2.5"
            fatDisplay.text = "0.3"
            carbsDisplay.text = "35"

            barcode = 3

            food_name = "Crunchy nut"
            protein = "2.5"
            fat = "0.3"
            carbs = "35"

            something3.alpha = 1f

            //de-select the other options when we pick an image
            something2.isSelected = false
            something2.alpha = 0.5f
            something1.isSelected = false
            something1.alpha = 0.5f
            something4.isSelected = false
            something4.alpha = 0.5f
            something5.isSelected = false
            something5.alpha = 0.5f
        }

        something4.setOnClickListener {
            something4.isSelected = !something4.isSelected
            drawableSelected = R.drawable.lucky_charms

            proteinDisplay.text = "1.4"
            fatDisplay.text = "0.5"
            carbsDisplay.text = "45"

            barcode = 4

            food_name = "Lucky charms"
            protein = "1.4"
            fat = "0.5"
            carbs = "45"

            something4.alpha = 1f

            //de-select the other options when we pick an image
            something2.isSelected = false
            something2.alpha = 0.5f
            something3.isSelected = false
            something3.alpha = 0.5f
            something1.isSelected = false
            something1.alpha = 0.5f
            something5.isSelected = false
            something5.alpha = 0.5f
        }

        something5.setOnClickListener {
            something5.isSelected = !something5.isSelected
            drawableSelected = R.drawable.weetabix

            proteinDisplay.text = "4.5"
            fatDisplay.text = "0.8"
            carbsDisplay.text = "26"

            barcode = 5

            food_name = "Weetabix"
            protein = "4.5"
            fat = "0.8"
            carbs = "26"

            something5.alpha = 1f

            //de-select the other options when we pick an image
            something2.isSelected = false
            something2.alpha = 0.5f
            something3.isSelected = false
            something3.alpha = 0.5f
            something4.isSelected = false
            something4.alpha = 0.5f
            something1.isSelected = false
            something1.alpha = 0.5f
        }

    }





}