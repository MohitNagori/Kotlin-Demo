package com.example.mohit.communicationdemo.nonintentCommunication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.mohit.communicationdemo.R

class CommunicationActivity : AppCompatActivity(), FirstFragment.IFragmentToActivityListener {
    override fun addNumbers(firstNo: Int, secondNo: Int) {
        Toast.makeText(this,"The sum is "+(firstNo + secondNo),Toast.LENGTH_LONG).show();
    }

    override fun communicateToOtherFragment(message: String) {
        try {
            val bundle = Bundle()
            bundle.putString("Message", message)
            val secondFragment = SecondFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, secondFragment)
            transaction.addToBackStack(null)
            transaction.commit()
            secondFragment.arguments = bundle
        } catch (e: Exception) {
            Log.v("Error", e.localizedMessage)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_communication)

        var buttonAtoF : Button = findViewById(R.id.buttonAtoF)
        buttonAtoF.setOnClickListener {
            try {
                val bundle = Bundle()
                bundle.putString("Message", "Called from Main Activity")
                val newFragment = FirstFragment()
                val transaction = supportFragmentManager.beginTransaction()
                transaction.add(R.id.fragment_container, newFragment)
                transaction.commit()
                newFragment.arguments = bundle
                buttonAtoF.isActivated = false
                buttonAtoF.visibility = View.INVISIBLE
            } catch (e: Exception) {
                Log.v("Error", e.localizedMessage)
            }

        };
    }
}