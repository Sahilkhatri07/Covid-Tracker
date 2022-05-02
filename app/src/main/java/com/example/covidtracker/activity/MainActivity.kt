package com.example.covidtracker.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.example.covidtracker.R
import com.example.covidtracker.fragment.InfoFragment
import com.example.covidtracker.fragment.TrackerFragment
import com.example.covidtracker.fragment.VaccineFragment
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var navigation : BottomNavigationView
    lateinit var frame : FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation = findViewById(R.id.navigation)
        frame = findViewById(R.id.frame)


        //Dashboard Fragment
        openDashboard()

        //Bottom navigation handling
        navigation.setOnNavigationItemSelectedListener {

            when(it.itemId){
                R.id.info->{
                    supportActionBar?.title = "Dashboard"
                    openDashboard()
                }
                R.id.vaccine->{
                    supportFragmentManager.beginTransaction().replace(R.id.frame,VaccineFragment()).commit()
                    supportActionBar?.title = "Vaccination"
                }
                R.id.tracker->{
                    supportFragmentManager.beginTransaction().replace(R.id.frame,TrackerFragment()).commit()
                    supportActionBar?.title = "Tracker"
                }
            }

            return@setOnNavigationItemSelectedListener true
        }

    }

    //For Dashboard
    fun openDashboard()
    {
       supportActionBar?.title = "Dashboard"
        supportFragmentManager.beginTransaction().replace(R.id.frame,InfoFragment()).commit()
    }

}