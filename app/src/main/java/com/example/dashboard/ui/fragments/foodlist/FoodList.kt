package com.example.dashboard.ui.fragments.foodlist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboard.R
import com.example.dashboard.data.models.Food
import com.example.dashboard.ui.activities.BMI
import com.example.dashboard.ui.activities.MainActivity
import com.example.dashboard.ui.animations.startAnimation
import com.example.dashboard.ui.fragments.foodlist.adapters.FoodListAdapter
import com.example.dashboard.ui.viewmodels.FoodViewModel
import kotlinx.android.synthetic.main.fragment_food_list.*
import java.util.*

class FoodList : Fragment(R.layout.fragment_food_list) {

    private lateinit var foodList: List<Food>
    private lateinit var foodViewModel: FoodViewModel
    private lateinit var adapter: FoodListAdapter


    private lateinit var deletedfood: String

    private var List = mutableListOf<String>()
    private var displayList = mutableListOf<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animation = AnimationUtils.loadAnimation(this.context, R.anim.circle_explosion_anim).apply {
            duration = 700
            interpolator = AccelerateDecelerateInterpolator()
        }

        adapter = FoodListAdapter(List)
        rv_foods.adapter = adapter
        rv_foods.layoutManager = LinearLayoutManager(context)

        //Instantiate and create viewmodel observers
        viewModels()

        displayList.addAll(listOf(adapter.toString()))
        //animation of the adding of the food
        fab_add.setOnClickListener {
            fab_add.isVisible = false
            circle.isVisible = true
            circle.startAnimation(animation) {
            findNavController().navigate(R.id.action_foodList_to_createFoodItem)
                circle.isVisible = false}
        }

        //Show the options menu in this fragment
        setHasOptionsMenu(true)

        swipeToRefresh.setOnRefreshListener {
            adapter.setData(foodList)
            swipeToRefresh.isRefreshing = false
        }


    }

    private fun viewModels() {
        foodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)

        foodViewModel.getAllFoods.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
            foodList = it

            //displayList.addAll(listOf(it.toString()))

            if (it.isEmpty()) {
                rv_foods.visibility = View.GONE
                tv_emptyView.visibility = View.VISIBLE
            } else {
                rv_foods.visibility = View.VISIBLE
                tv_emptyView.visibility = View.GONE

                //here it is to show add food if the list is empty
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu, menu)


        var item: MenuItem = menu!!.findItem(R.id.action_search)

        if (item != null) {
            var searchView = item.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty()) {
                        displayList.clear()
                        var search = newText.lowercase(Locale.getDefault())

                        for (food in List) {
                            if (food.lowercase(Locale.getDefault()).contains(search)) {
                                displayList.add(food)

                            }
                            rv_foods.adapter!!.notifyDataSetChanged()
                        }
                    } else {
                        displayList.clear()
                        displayList.addAll(List)

                    }

                    return true


                }


            })

            return super.onCreateOptionsMenu(menu, inflater)
        }


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this.context, MainActivity::class.java)
        when (item.itemId) {
            R.id.nav_delete -> foodViewModel.deleteAllFoods()
        }
        when (item.itemId){
            R.id.action_home -> startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }



}