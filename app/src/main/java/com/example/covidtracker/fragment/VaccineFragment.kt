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
import android.app.DatePickerDialog
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.covidtracker.CovidAdapter
import com.example.covidtracker.R
import com.example.covidtracker.activity.ConnectionManager
import com.android.volley.Request
import com.android.volley.Response
import com.example.covidtracker.Covid
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONException
import java.util.*
import kotlin.collections.HashMap

class VaccineFragment : Fragment() {

    lateinit var searchTwo: EditText
    lateinit var searchbarOne: ImageButton
    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var covidAdapter: CovidAdapter
    lateinit var letFloat: FloatingActionButton
    lateinit var check: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_vaccine, container, false)

        searchTwo = view.findViewById(R.id.searchTwo)
        searchbarOne = view.findViewById(R.id.searchbarOne)
        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)
        letFloat = view.findViewById(R.id.letFloat)
        check = view.findViewById(R.id.check)


        //Getting calender Instance
        val c = Calendar.getInstance()
        val years = c.get(Calendar.YEAR)
        val months = c.get(Calendar.MONTH)
        val days = c.get(Calendar.DAY_OF_MONTH)
        val value: String? = null


        //Floating Button Action for Calaneder view
        letFloat.setOnClickListener {
            val date = DatePickerDialog(
                activity as Context,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                    check.text = ("" + dayOfMonth + "" + month + "" + year)


                },
                years,
                months,
                days
            )
            date.show()
        }

        //On searching the pinCode
        searchbarOne.setOnClickListener {
            val queue = Volley.newRequestQueue(activity as Context)
            val search = searchTwo.text.toString()

            val url =
                "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?pincode=$search&date=12072021"

            if (ConnectionManager().checkConnectivity(activity as Context)) {
                val jsonObjectRequest = object :
                    JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { response ->

                        try {
                            println("Response $response")
                            val data = response.getJSONArray("sessions")
                            val covidNine = arrayListOf<Covid>()
                            for (i in 0 until data.length()) {
                                val jsonObject = data.getJSONObject(i)

                                val covidJson = Covid(
                                    jsonObject.getString("name"),
                                    jsonObject.getString("address"),
                                    jsonObject.getString("from"),
                                    jsonObject.getString("to"),
                                    jsonObject.getString("vaccine"),
                                    jsonObject.getString("fee_type"),
                                    jsonObject.getString("min_age_limit"),
                                    jsonObject.getString("available_capacity")
                                )
                                covidNine.add(covidJson)

                                covidAdapter = CovidAdapter(activity as Context, covidNine)
                                layoutManager = LinearLayoutManager(activity)

                                recyclerDashboard.adapter = covidAdapter
                                recyclerDashboard.layoutManager = layoutManager

                            }

                        } catch (e: JSONException) {
                            Toast.makeText(
                                activity as Context,
                                "JSON EXCEPTION",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                    },
                        Response.ErrorListener {
                            println("SOME 2 $it")
                        }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["User-Agent"] = "Mozilla/5.0"
                        return headers
                    }
                }

                queue.add(jsonObjectRequest)

            } else {
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Error")
                dialog.setMessage("Internet Not Found")
                dialog.setPositiveButton("Opem Settings") { tetx, listerner ->

                    val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(intent)
                    activity?.finish()

                }
                dialog.setNegativeButton("Exit") { text, listerner ->
                    ActivityCompat.finishAffinity(activity as Activity)
                }
                dialog.create()
                dialog.show()
            }

        }

        return view
    }
}
