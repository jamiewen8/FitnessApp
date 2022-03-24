package com.example.dashboard.ui.fragments.createfood


import android.annotation.SuppressLint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import com.example.dashboard.databinding.FragmentCreateFoodItemBinding

import com.example.dashboard.ui.viewmodels.FoodViewModel



class CreateFoodItem : Fragment() {

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

        binding.btnConfirm.setOnClickListener{

        }

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
                    binding.name.text = it[0].food_name
                    binding.protein.text = it[0].protein
                    binding.fat.text = it[0].fat
                    binding.carbs.text = it[0].carbs
                }
            }

        }
    }


}