package com.example.dashboard.ui.fragments.createfoodfromscanner


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
import com.example.dashboard.databinding.FragmentCreateFoodItemBinding
import com.example.dashboard.ui.viewmodels.FoodViewModel
import kotlinx.android.synthetic.main.fragment_create_food_item.*
import java.util.*


class CreateFoodItemScanner : Fragment(R.layout.scanner_create_food_item) {

    private var _binding = FragmentCreateFoodItemBinding? null


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {







        //Selected and image to put into our list
        //drawableSelected()

    }

    //todo get rid of the measurement thing





}