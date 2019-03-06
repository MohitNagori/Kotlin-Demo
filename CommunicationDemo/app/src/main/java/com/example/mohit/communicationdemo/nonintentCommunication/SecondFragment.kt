package com.example.mohit.communicationdemo.nonintentCommunication

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.mohit.communicationdemo.R


class SecondFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_second, container, false)
        val strtext = arguments!!.getString("Message")
        val fragmentAText = rootView.findViewById(R.id.fragmentBText) as TextView
        fragmentAText.append(" " + strtext!!)
        Toast.makeText(context, strtext, Toast.LENGTH_SHORT).show()
        return rootView
    }
}