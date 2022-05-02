package com.example.covidtracker

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class CovidAdapter(val context: Context,val itemList : ArrayList<Covid>) : RecyclerView.Adapter<CovidAdapter.CovidViewHolder>() {

    class CovidViewHolder(view:View) : RecyclerView.ViewHolder(view) {
        val hospitalName : TextView = view.findViewById(R.id.hospitalName)
        val addressName : TextView = view.findViewById(R.id.addressName)
        val fromTime : TextView = view.findViewById(R.id.fromTime)
        val toTime : TextView = view.findViewById(R.id.toTime)
        val vaccineName : TextView = view.findViewById(R.id.vaccineName)
        val free : TextView = view.findViewById(R.id.free)
        val ageNumber : TextView = view.findViewById(R.id.ageNumber)
        val seatNumber : TextView = view.findViewById(R.id.seatNumber)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CovidViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_layout,parent,false)
        return CovidViewHolder(view)
    }

    override fun onBindViewHolder(holder: CovidViewHolder, position: Int) {
        val text = itemList[position]
        holder.hospitalName.text = text.name
        holder.addressName.text = text.address
        holder.fromTime.text = text.from
        holder.toTime.text = text.to
        holder.vaccineName.text = text.vaccineOne
        holder.free.text = text.type
        holder.ageNumber.text = text.age
        holder.seatNumber.text = text.avaiability
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}