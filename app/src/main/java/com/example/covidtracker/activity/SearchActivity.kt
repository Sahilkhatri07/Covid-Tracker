package com.example.covidtracker.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.covidtracker.R
import com.android.volley.Request
import com.android.volley.Response
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject

class SearchActivity : AppCompatActivity() {

    lateinit var searchOne: EditText
    lateinit var searchBar: ImageButton
    lateinit var flag: ImageView
    lateinit var countryName: TextView
    lateinit var casesTotalNumber: TextView
    lateinit var deathTotalNumber: TextView
    lateinit var recoveredTotalNumber: TextView
    lateinit var todayCasesNumber: TextView
    lateinit var todayDeathNumber: TextView
    lateinit var recoveredTodayNumber: TextView
    lateinit var activeTotalNumber: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        searchOne = findViewById(R.id.searchOne)
        searchBar = findViewById(R.id.searchbar)
        flag = findViewById(R.id.flag)
        countryName = findViewById(R.id.countryName)
        casesTotalNumber = findViewById(R.id.casesTotalNumber)
        deathTotalNumber = findViewById(R.id.deathTotalNumber)
        recoveredTotalNumber = findViewById(R.id.recoveredTotalNumber)
        todayCasesNumber = findViewById(R.id.todayCasesNumber)
        todayDeathNumber = findViewById(R.id.todayDeathNumber)
        recoveredTodayNumber = findViewById(R.id.recoveredTodayNumber)
        activeTotalNumber = findViewById(R.id.activeTotalNumber)


        //By defualt USA Data covid
        check()


        //Searching for the country name
        searchBar.setOnClickListener {
            check()
        }

    }

    //Function
    fun check() {
        val name = searchOne.text.toString()
        val queue = Volley.newRequestQueue(this)
        val url = "https://disease.sh/v3/covid-19/countries/usa"
        val link = "https://disease.sh/v3/covid-19/countries/$name"

        if (name.isEmpty()) {
            if (ConnectionManager().checkConnectivity(this)) {

                val jsonObjectRequest = object :
                    JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { response ->

                        try {
                            println("Response $response")

                            countryName.text = response.getString("country")
                            casesTotalNumber.text = response.getString("cases")
                            todayCasesNumber.text = response.getString("todayCases")
                            deathTotalNumber.text = response.getString("deaths")
                            todayDeathNumber.text = response.getString("todayDeaths")
                            recoveredTotalNumber.text = response.getString("recovered")
                            recoveredTodayNumber.text = response.getString("todayRecovered")
                            activeTotalNumber.text = response.getString("active")

                            val jsonObject = response.getJSONObject("countryInfo")
                            Picasso.get().load(jsonObject.getString("flag")).into(flag)


                        } catch (e: JSONException) {
                            Toast.makeText(this, "EXCEPTION 1", Toast.LENGTH_SHORT).show()
                        }

                    },
                        Response.ErrorListener {
                            println("SOME 4 $it")
                        }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["User-Agent"] = "Mozilla/5.0"
                        return headers
                    }
                }
                queue.add(jsonObjectRequest)

            } else {
                val dialog = AlertDialog.Builder(this)
                dialog.setTitle("Error")
                dialog.setMessage("No Internet")
                dialog.setPositiveButton("Open Settings") { text, listerner ->

                    val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(intent)
                    this.finish()
                }
                dialog.setNegativeButton("Exit") { text, listerner ->
                    ActivityCompat.finishAffinity(this as Activity)
                }
                dialog.create()
                dialog.show()

            }

        } else {
            if (ConnectionManager().checkConnectivity(this)) {
                val jsonObjectRequests = object : JsonObjectRequest(Request.Method.GET,
                    link,
                    null,
                    Response.Listener { response ->

                        try {
                            println("Response $response")

                            countryName.text = response.getString("country")
                            casesTotalNumber.text = response.getString("cases")
                            todayCasesNumber.text = response.getString("todayCases")
                            deathTotalNumber.text = response.getString("deaths")
                            todayDeathNumber.text = response.getString("todayDeaths")
                            recoveredTotalNumber.text = response.getString("recovered")
                            recoveredTodayNumber.text = response.getString("todayRecovered")
                            activeTotalNumber.text = response.getString("active")

                            val jsonObject = response.getJSONObject("countryInfo")
                            Picasso.get().load(jsonObject.getString("flag")).into(flag)


                        } catch (e: JSONException) {
                            Toast.makeText(this, "EXCEPTION 2", Toast.LENGTH_SHORT).show()
                        }

                    },
                    Response.ErrorListener {
                        println("SOME 3 $it")
                    }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["User-Agent"] = "Mozilla/5.0"
                        return headers
                    }
                }
                queue.add(jsonObjectRequests)
            } else {
                val dialog = AlertDialog.Builder(this)
                dialog.setTitle("Error")
                dialog.setMessage("No Internet")
                dialog.setPositiveButton("Open Settings") { text, listerner ->

                    val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(intent)
                    this.finish()
                }
                dialog.setNegativeButton("Exit") { text, listerner ->
                    ActivityCompat.finishAffinity(this as Activity)
                }
                dialog.create()
                dialog.show()

            }
        }

    }

}