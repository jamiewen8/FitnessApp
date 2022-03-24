package com.example.dashboard.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.dashboard.R
import com.example.dashboard.databinding.ActivityDiaryBinding
import com.example.dashboard.ui.fragments.add_food
import com.example.dashboard.ui.fragments.see_diary
import kotlinx.android.synthetic.main.activity_diary.*

class DiaryActivity: AppCompatActivity() {

    lateinit var binding: ActivityDiaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.addFood.setOnClickListener{
            replaceFragment(add_food())
        }

        binding.seeDiary.setOnClickListener{
            replaceFragment(see_diary())
        }

        val scannedBarcode = intent.getStringExtra("barcode")
        if(scannedBarcode != null && scannedBarcode.toInt() <= 5)
        {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val myFragment = add_food()
            val bundle = Bundle()
            bundle.putString("barcode", scannedBarcode)
            myFragment.arguments = bundle
            fragmentTransaction.add(R.id.fragmentContainer, myFragment).commit()

        }
        else if( scannedBarcode != null && scannedBarcode.toInt() > 5)
        {
            Toast.makeText(this, "Item out of current dictionary", Toast.LENGTH_SHORT).show()
        }


    }


    private fun replaceFragment(fragment : Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer,fragment)

        fragmentTransaction.commit()
    }


}