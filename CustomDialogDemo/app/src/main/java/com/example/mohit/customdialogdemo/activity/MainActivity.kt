package com.example.mohit.customdialogdemo.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import com.example.mohit.customdialogdemo.R
import com.example.mohit.customdialogdemo.customdialog.ContactConfirmDialog

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.submitButton).setOnClickListener {
            val bundle = Bundle()
            bundle.putString(ContactConfirmDialog.CUSTOMDIALOG_NAME, findViewById<EditText>(R.id.name).getText().toString())
            bundle.putString(ContactConfirmDialog.CUSTOMDIALOG_NUMBER, findViewById<EditText>(R.id.number).getText().toString())
            ContactConfirmDialog(this@MainActivity, bundle).show()
        }

    }
}
