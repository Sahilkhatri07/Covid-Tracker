package com.example.covidtracker.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.covidtracker.R


class InfoFragment : Fragment() {

    lateinit var callNow: Button
    lateinit var messageNow: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_info, container, false)


        callNow = view.findViewById(R.id.callNow)
        messageNow = view.findViewById(R.id.messageNow)


         //For call
        callNow.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + "+911077")
            startActivity(intent)
        }


        //For Message
        messageNow.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            val chooser = Intent.createChooser(intent, "Please share this")
            startActivity(chooser)
        }

        return view
    }
}