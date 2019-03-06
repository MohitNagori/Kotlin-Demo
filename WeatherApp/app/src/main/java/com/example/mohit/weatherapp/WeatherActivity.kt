package com.example.mohit.weatherapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, WeatherFragment())
                    .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.weather, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.change_city) {
            showInputDialog()
        }
        return false
    }

    private fun showInputDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Change city")
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)
        builder.setPositiveButton("Go") { dialog, which -> changeCity(input.text.toString()) }
        builder.show()
    }

    fun changeCity(city: String) {
        val wf = supportFragmentManager
                .findFragmentById(R.id.container) as WeatherFragment
        wf.changeCity(city)
        CityPreference(this).city = city
    }
}
