package com.example.dashboard.ui.fragments.createfood


import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
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

class CreateFoodItem : Fragment() {

    private var drawableSelected = 0
    private var food_name = ""
    private var protein = 0
    private var fat = 0
    private var carbs = 0


    companion object {
        fun newInstance() = CreateFoodItem()
    }

    val viewModel: FoodViewModel by viewModels()

    private var _binding: CreateFoodItem? = null



    private val binding get() = _binding!!



//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        //observerSetup()
//
//    }




//    private fun observerSetup(){
//        viewModel.getSearchResults().observe(this) { foods ->
//            foods?.let {
//                if (it.isNotEmpty()) {
//                    binding.something1.setImageBitmap(it[0].imageId)
//                    binding.
//                }
//            }
//
//        }
//    }





}