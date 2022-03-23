package com.example.dashboard.ui.fragments.createfood


import android.annotation.SuppressLint
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
import com.example.dashboard.databinding.FragmentCreateFoodItemBinding

import com.example.dashboard.ui.viewmodels.FoodViewModel
import kotlinx.android.synthetic.main.fragment_create_food_item.*
import java.util.*



class CreateFoodItem : Fragment() {



    private var food_name = ""
    private var protein = 0
    private var fat = 0
    private var carbs = 0


    companion object {
        fun newInstance() = CreateFoodItem()
    }

    private var _binding: FragmentCreateFoodItemBinding? = null
    private val binding get() = _binding!!

    val viewModel: FoodViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateFoodItemBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        listenerSetup()
        observerSetup()

    }


    private fun listenerSetup(){

        binding.something1.setOnClickListener{
            viewModel.findFood(1)
            binding.something2.alpha = 0.5f
            binding.something3.alpha = 0.5f
            binding.something4.alpha = 0.5f
            binding.something5.alpha = 0.5f
            binding.something1.alpha = 1f
        }
        binding.something2.setOnClickListener{
            viewModel.findFood(2)
            binding.something1.alpha = 0.5f
            binding.something3.alpha = 0.5f
            binding.something4.alpha = 0.5f
            binding.something5.alpha = 0.5f
            binding.something2.alpha = 1f
        }
        binding.something3.setOnClickListener{
            viewModel.findFood(3)
            binding.something1.alpha = 0.5f
            binding.something2.alpha = 0.5f
            binding.something4.alpha = 0.5f
            binding.something5.alpha = 0.5f
            binding.something3.alpha = 1f
        }
        binding.something4.setOnClickListener{
            viewModel.findFood(4)
            binding.something1.alpha = 0.5f
            binding.something2.alpha = 0.5f
            binding.something3.alpha = 0.5f
            binding.something5.alpha = 0.5f
            binding.something4.alpha = 1f
        }
        binding.something5.setOnClickListener{
            viewModel.findFood(5)
            binding.something2.alpha = 0.5f
            binding.something3.alpha = 0.5f
            binding.something4.alpha = 0.5f
            binding.something1.alpha = 0.5f
            binding.something5.alpha = 1f
        }

    }


    @SuppressLint("FragmentLiveDataObserve")
    private fun observerSetup() {
        viewModel.getSearchResults().observe(this) { foods ->
            foods?.let {
                if (it.isNotEmpty()) {
                    binding.protein.text = it[0].protein
                    binding.fat.text = it[0].fat
                    binding.carbs.text = it[0].carbs
                }
            }

        }
    }


}