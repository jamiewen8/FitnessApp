package com.example.dashboard.ui.fragments.choose

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dashboard.R
import kotlinx.android.synthetic.main.fragment_choose.*
import kotlinx.android.synthetic.main.fragment_food_list.*

class Choose : Fragment(R.layout.fragment_choose) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scannerbtn.setOnClickListener {
            findNavController().navigate(R.id.action_choose_to_scanner)
        }
        choosebtn.setOnClickListener {
            findNavController().navigate(R.id.action_choose_to_createFoodItem)
        }

    }
}