package com.example.dashboard.ui.fragments.foodlist.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboard.R
import com.example.dashboard.data.models.Food
import com.example.dashboard.ui.fragments.foodlist.FoodListDirections
import kotlinx.android.synthetic.main.recycler_food_item.view.*

class FoodListAdapter : RecyclerView.Adapter<FoodListAdapter.MyViewHolder>() {

    var foodsList = emptyList<Food>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.cv_cardView.setOnClickListener {
                val position = adapterPosition
                Log.d("FoodsListAdapter", "Item clicked at: $position")

                val action =
                    FoodListDirections.actionFoodListToCreateFoodItem()
                itemView.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_food_item, parent,false))
    }

    //todo: initialise the recycler view and set it up to show data (part2)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentFood = foodsList[position]
        holder.itemView.iv_food_icon.setImageResource(currentFood.imageId)
        holder.itemView.tv_item_title.text = currentFood.food_name
        holder.itemView.proteinrecycle.text = currentFood.protein.toString()
        holder.itemView.fatrecycle.text = currentFood.fat.toString()
        holder.itemView.carbsrecycle.text = currentFood.carbs.toString()
    }

    override fun getItemCount(): Int {
        return foodsList.size
    }

    fun setData(food: List<Food>) {
        this.foodsList = food
        notifyDataSetChanged()
    }


}