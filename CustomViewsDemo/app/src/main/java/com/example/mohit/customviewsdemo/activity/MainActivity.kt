package com.example.mohit.customviewsdemo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

import com.example.mohit.customviewsdemo.R
import com.example.mohit.customviewsdemo.customviews.DateView
import com.example.mohit.customviewsdemo.customviews.StyleButton

class MainActivity : AppCompatActivity() {

    internal lateinit var dateView: DateView
    internal lateinit var styleButton: StyleButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Textview
        dateView = findViewById(R.id.dateView)

        val delimiterSpinner = findViewById<View>(R.id.delimiterSpinner) as Spinner
        val delimiterAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayOf("-", "/"))
        delimiterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        delimiterSpinner.adapter = delimiterAdapter
        delimiterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position == 0) {
                    dateView.setDelimiter("-")
                } else {
                    dateView.setDelimiter("/")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        val fancyTextSpinner = findViewById<View>(R.id.fancyTextSpinner) as Spinner
        val fancyTextAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayOf("true", "false"))
        fancyTextAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fancyTextSpinner.adapter = fancyTextAdapter

        fancyTextSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position == 0) {
                    dateView.setFancyText(true)
                } else {
                    dateView.setFancyText(false)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


        // Button
        styleButton = findViewById(R.id.styleButton)
        val headingSpinner = findViewById<View>(R.id.headingSpinner) as Spinner
        val headingAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayOf("Morning", "Afternoon", "Evening"))
        headingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        headingSpinner.adapter = headingAdapter
        headingSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                styleButton.setHeading(headingAdapter.getItem(position)!!.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        val subHeadingSpinner = findViewById<View>(R.id.subHeadingSpinner) as Spinner
        val subHeadingAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayOf("Mohit", "Nagori"))
        subHeadingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        subHeadingSpinner.adapter = subHeadingAdapter

        subHeadingSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                styleButton.setSubheading(subHeadingAdapter.getItem(position)!!.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }
}