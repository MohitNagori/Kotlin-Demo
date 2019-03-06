package com.example.mohit.weatherapp

/**
 * Created by Mohit on 4/20/2018.
 */

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

import org.json.JSONObject

import android.content.Context

object RemoteFetch {

    private val OPEN_WEATHER_MAP_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric"

    fun getJSON(context: Context, city: String): JSONObject? {
        try {
            val url = URL(String.format(OPEN_WEATHER_MAP_URL, city))
            val connection = url.openConnection() as HttpURLConnection

            connection.addRequestProperty("x-api-key",
                    context.getString(R.string.open_weather_maps_app_id))

            val reader = BufferedReader(
                    InputStreamReader(connection.inputStream))

            val json = StringBuffer(1024)

            var line : String?
            do {
                line = reader.readLine()
                if (line == null)
                    break
                json.append(line).append("\n")

            } while (true)
            
            reader.close()

            val data = JSONObject(json.toString())

            // This value will be 404 if the request was not
            // successful
            return if (data.getInt("cod") != 200) {
                null
            } else data

        } catch (e: Exception) {
            return null
        }

    }
}