package com.example.dashboard.ui.fragments.foodlist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dashboard.R
import com.example.dashboard.data.models.Food
import com.example.dashboard.ui.fragments.foodlist.adapters.FoodListAdapter
import com.example.dashboard.ui.viewmodels.FoodViewModel
import kotlinx.android.synthetic.main.fragment_food_list.*

class FoodList : Fragment(R.layout.fragment_food_list) {

    /*private lateinit var foodList: List<Food>
    private lateinit var foodViewModel: FoodViewModel
    private lateinit var adapter: FoodListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FoodListAdapter()
        rv_habits.adapter = adapter
        rv_habits.layoutManager = LinearLayoutManager(context)

        //Instantiate and create viewmodel observers
        //viewModels()

        fab_add.setOnClickListener {
            findNavController().navigate(R.id.action_foodList_to_createFoodItem)
        }

        //Show the options menu in this fragment
        setHasOptionsMenu(true)

        swipeToRefresh.setOnRefreshListener {
            adapter.setData(foodList)
            swipeToRefresh.isRefreshing = false
        }
    }
*/
//    private fun viewModels() {
////        foodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)
////
////        foodViewModel.getAllFoods.observe(viewLifecycleOwner, Observer {
////            adapter.setData(it)
////            foodList = it
////
////            if (it.isEmpty()) {
////                rv_habits.visibility = View.GONE
////                tv_emptyView.visibility = View.VISIBLE
////            } else {
////                rv_habits.visibility = View.VISIBLE
////                tv_emptyView.visibility = View.GONE
////            }
////        })
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu, menu)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.nav_delete -> foodViewModel.deleteAllFoods()
//        }
//        return super.onOptionsItemSelected(item)
//    }

}