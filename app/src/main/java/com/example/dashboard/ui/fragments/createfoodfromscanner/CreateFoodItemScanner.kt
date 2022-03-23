package com.example.dashboard.ui.fragments.createfoodfromscanner



import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.dashboard.R
import com.example.dashboard.ui.viewmodels.FoodViewModel


class CreateFoodItemScanner : Fragment(R.layout.scanner_create_food_item) {


    val viewModel: FoodViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {


    }

    private fun observerSetup() {
        viewModel.getSearchResults().observe(this) { foods ->
            foods?.let {
                if (it.isNotEmpty()) {
                    it[0].food_name

                }
            }


        }
    }






}