package com.example.dashboard.ui.fragments.createfoodfromscanner


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dashboard.R
import com.example.dashboard.databinding.ScannerCreateFoodItemBinding
import com.example.dashboard.ui.fragments.foodlist.adapters.FoodListAdapter
import com.example.dashboard.ui.viewmodels.FoodViewModel



class CreateFoodItemScanner : Fragment() {

    private var adapter: FoodListAdapter? = null
    val viewModel: FoodViewModel by activityViewModels()


    companion object {
        fun newInstance() = CreateFoodItemScanner()
    }

    private var _binding: ScannerCreateFoodItemBinding? = null
    private val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = ScannerCreateFoodItemBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        listenerSetup()
        observerSetup()

    }

    private fun listenerSetup() {

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



//    private fun recyclerSetup(){
//        adapter = FoodListAdapter(R.layout.scanner_create_food_item)
//        binding.FoodRecycler.adapter = adapter
//    }
}