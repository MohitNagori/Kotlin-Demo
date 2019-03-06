package com.example.mohit.weatherapp

import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import org.json.JSONObject

import java.sql.Date
import java.text.DateFormat
import java.util.Calendar
import java.util.Locale


class WeatherFragment : Fragment() {
    internal var weatherFont: Typeface? = null

    internal var cityField: TextView? = null
    internal var updatedField: TextView? = null
    internal var detailsField: TextView? = null
    internal var currentTemperatureField: TextView? = null
    internal var weatherIcon: TextView? = null

    internal var handler: Handler

    init {
        handler = Handler()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_weather, container, false)
        cityField = rootView.findViewById<View>(R.id.city_field) as TextView
        updatedField = rootView.findViewById<View>(R.id.updated_field) as TextView
        detailsField = rootView.findViewById<View>(R.id.details_field) as TextView
        currentTemperatureField = rootView.findViewById<View>(R.id.current_temperature_field) as TextView
        weatherIcon = rootView.findViewById<View>(R.id.weather_icon) as TextView

        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weatherFont = Typeface.createFromAsset(activity.assets, "fonts/weather.ttf")
        weatherIcon?.typeface = weatherFont
        updateWeatherData(CityPreference(activity).city)
    }

    private fun updateWeatherData(city: String) {
        object : Thread() {
            override fun run() {
                val json = RemoteFetch.getJSON(activity, city)
                if (json == null) {
                    handler.post {
                        Toast.makeText(activity,
                                activity.getString(R.string.place_not_found),
                                Toast.LENGTH_LONG).show()
                    }
                } else {
                    handler.post { renderWeather(json) }
                }
            }
        }.start()
    }

    private fun renderWeather(json: JSONObject?) {
        try {
            cityField?.setText(json!!.getString("name").toUpperCase(Locale.US) +
                    ", " +
                    json.getJSONObject("sys").getString("country"))

            val details = json!!.getJSONArray("weather").getJSONObject(0)
            val main = json.getJSONObject("main")
            detailsField?.setText(
                    details.getString("description").toUpperCase(Locale.US) +
                            "\n" + "Humidity: " + main.getString("humidity") + "%" +
                            "\n" + "Pressure: " + main.getString("pressure") + " hPa")

            currentTemperatureField?.setText(
                    String.format("%.2f", main.getDouble("temp")) + " ℃")

            val df = DateFormat.getDateTimeInstance()
            val updatedOn = df.format(Date(json.getLong("dt") * 1000))
            updatedField?.text = "Last update: $updatedOn"

            setWeatherIcon(details.getInt("id"),
                    json.getJSONObject("sys").getLong("sunrise") * 1000,
                    json.getJSONObject("sys").getLong("sunset") * 1000)

        } catch (e: Exception) {
            Log.e("SimpleWeather", "One or more fields not found in the JSON data")
        }

    }

    private fun setWeatherIcon(actualId: Int, sunrise: Long, sunset: Long) {
        val id = actualId / 100
        var icon = ""
        if (actualId == 800) {
            val calendar = Calendar.getInstance()
            val currentTime = calendar.time.time
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = activity.getString(R.string.weather_sunny)
            } else {
                icon = activity.getString(R.string.weather_clear_night)
            }
        } else {
            when (id) {
                2 -> icon = activity.getString(R.string.weather_thunder)
                3 -> icon = activity.getString(R.string.weather_drizzle)
                7 -> icon = activity.getString(R.string.weather_foggy)
                8 -> icon = activity.getString(R.string.weather_cloudy)
                6 -> icon = activity.getString(R.string.weather_snowy)
                5 -> icon = activity.getString(R.string.weather_rainy)
            }
        }
        weatherIcon?.text = icon
    }

    fun changeCity(city: String) {
        updateWeatherData(city)
    }
}