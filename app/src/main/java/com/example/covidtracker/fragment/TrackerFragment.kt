package com.example.covidtracker.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.covidtracker.R
import com.example.covidtracker.activity.ConnectionManager
import com.android.volley.Request
import com.android.volley.Response
import com.example.covidtracker.activity.SearchActivity
import org.json.JSONException


class TrackerFragment : Fragment() {


    lateinit var casesTotalNumber : TextView
    lateinit var recoveredTotalNumber : TextView
    lateinit var deathTotalNumber : TextView
    lateinit var activeTotalNumber : TextView
    lateinit var todayCasesNumber : TextView
    lateinit var todayDeathNumber : TextView
    lateinit var todayRecoveredNumber : TextView
    lateinit var country : Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tracker, container, false)

        casesTotalNumber = view.findViewById(R.id.casesTotalNumber)
        recoveredTotalNumber = view.findViewById(R.id.recoveredTotalNumber)
        deathTotalNumber = view.findViewById(R.id.deathTotalNumber)
        activeTotalNumber = view.findViewById(R.id.activeTotalNumber)
        todayCasesNumber = view.findViewById(R.id.todayCasesNumber)
        todayDeathNumber = view.findViewById(R.id.todayDeathNumber)
        todayRecoveredNumber = view.findViewById(R.id.todayRecoveredNumber)
        country = view.findViewById(R.id.country)


        //Send user to Search Page
        country.setOnClickListener {
            val intent = Intent(activity as Context,SearchActivity::class.java)
            startActivity(intent)
        }


        // Gives World Covid Data
        tracker()

        return view
    }


    //Gives World Data
    fun tracker()
    {
        val queue = Volley.newRequestQueue(activity as Context)
        val url = "https://disease.sh/v3/covid-19/all"

        if(ConnectionManager().checkConnectivity(activity as Context))
        {

           val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET,url,null,Response.Listener { response->

             try{
                 println("Response $response")

                 casesTotalNumber.text = response.getString("cases")
                 recoveredTotalNumber.text = response.getString("recovered")
                 deathTotalNumber.text = response.getString("deaths")
                 activeTotalNumber.text = response.getString("active")
                 todayCasesNumber.text = response.getString("todayCases")
                 todayDeathNumber.text = response.getString("todayDeaths")
                 todayRecoveredNumber.text = response.getString("todayRecovered")


             }
             catch (e:JSONException)
             {
                 Toast.makeText(activity as Context,"JSON EXCEPTION",Toast.LENGTH_SHORT).show()
             }

           },
           Response.ErrorListener {
              println("SOME 1 $it")
           })
           {
               override fun getHeaders(): MutableMap<String, String> {
                   val headers = HashMap<String,String>()
                   headers["User-Agent"] = "Mozilla/5.0"
                   return headers
               }
           }
           queue.add(jsonObjectRequest)

        }
        else{
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("No Internet")
            dialog.setPositiveButton("Open Settings"){text,listerner->

                val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(intent)
                activity?.finish()

            }
            dialog.setNegativeButton("Exit"){text,listerner->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()
        }

    }

}