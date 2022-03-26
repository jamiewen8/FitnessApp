package com.example.dashboard.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.dashboard.R
import com.example.dashboard.databinding.SeeDiaryBinding
// com.example.dashboard.ui.fragments.foodlist.adapters.FoodListAdapter
import com.example.dashboard.ui.viewmodels.FoodViewModel
import kotlinx.android.synthetic.main.card_view_design.view.*

class see_diary : Fragment(R.layout.see_diary){

    ///private var adapter: FoodListAdapter? = null
    var displayFood : Int? = 0

    private var _binding: SeeDiaryBinding? = null
    private val binding get() = _binding!!

    val viewModel: FoodViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = SeeDiaryBinding.inflate(inflater, container, false)
        displayFood = arguments?.getString("message")?.toInt()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observerSetup()
        //recyclerview()
        displayFood?.let { viewModel.findFood(it) }

    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun observerSetup() {
        viewModel.getSearchResults().observe(this) { foods ->
            foods?.let {
                if (it.isNotEmpty()) {
                    binding.rvFood.textView.text = it[0].food_name

                }
            }

        }
    }



    /*private fun recyclerview() {
        adapter = FoodListAdapter(R.layout.recycler_food_item)
        binding.rvFood.layoutManager = LinearLayoutManager(context)
        binding.rvFood.adapter = adapter
    }*/
}