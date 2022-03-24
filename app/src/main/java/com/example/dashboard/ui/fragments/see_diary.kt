package com.example.dashboard.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dashboard.R
import com.example.dashboard.databinding.AddFoodBinding
import com.example.dashboard.databinding.FragmentCreateFoodItemBinding
import com.example.dashboard.databinding.SeeDiaryBinding
import com.example.dashboard.ui.fragments.foodlist.adapters.FoodListAdapter
import com.example.dashboard.ui.viewmodels.FoodViewModel

class see_diary : Fragment(R.layout.see_diary){

    private var adapter: FoodListAdapter? = null

    private var _binding: SeeDiaryBinding? = null
    private val binding get() = _binding!!

    val viewModel: FoodViewModel by viewModels()

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.selectedItem.observe(viewLifecycleOwner, Observer { item ->
            // Perform an action with the latest item data
        })
        _binding = SeeDiaryBinding.inflate(inflater, container, false)
        return binding.root


    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //recyclerview()

    }

    /*private fun recyclerview() {
        adapter = FoodListAdapter(R.layout.recycler_food_item)
        binding.rvFood.layoutManager = LinearLayoutManager(context)
        binding.rvFood.adapter = adapter
    }*/
}