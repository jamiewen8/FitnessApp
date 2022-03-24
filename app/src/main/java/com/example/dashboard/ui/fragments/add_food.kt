package com.example.dashboard.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.example.dashboard.R
import com.example.dashboard.data.models.Food
import com.example.dashboard.databinding.FragmentCreateFoodItemBinding
import com.example.dashboard.ui.activities.DiaryActivity
import com.example.dashboard.ui.activities.RewardPage
import com.example.dashboard.ui.fragments.createfood.CreateFoodItem
import com.example.dashboard.ui.viewmodels.FoodViewModel
import kotlin.math.absoluteValue

class add_food : Fragment(R.layout.add_food) {

    companion object {
        fun newInstance() = CreateFoodItem()
    }


    private var _binding: FragmentCreateFoodItemBinding? = null
    private val binding get() = _binding!!

    val viewModel: FoodViewModel by viewModels()

    var idNum: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateFoodItemBinding.inflate(inflater, container, false)
        return binding.root
        barcodeCheck()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        listenerSetup()
        observerSetup()

    }

    private fun barcodeCheck()
    {
        val bundle = arguments
        val value = bundle!!.getString("barcode")
        if (value != null ) {
            viewModel.findFood(value.toInt())
        }
    }

    private fun listenerSetup(){

        binding.btnConfirm.setOnClickListener{
            val food = Food(binding.name.text as String, binding.protein.text as String, binding.fat.text as String, binding.carbs.text as String, 1)
            viewModel.selectItem(food)
            val bundle = Bundle()
            bundle.putString("value", idNum.toString())
            val seeDiary = see_diary()

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
                    idNum =  it[0].id
                    binding.name.text = it[0].food_name
                    binding.protein.text = it[0].protein
                    binding.fat.text = it[0].fat
                    binding.carbs.text = it[0].carbs
                }
            }

        }
    }



}