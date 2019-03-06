package com.example.mohit.weatherapp

/**
 * Created by Mohit on 4/20/2018.
 */

import android.app.Activity
import android.content.SharedPreferences

class CityPreference(activity: Activity) {

    internal var prefs: SharedPreferences

    // If the user has not chosen a city yet, return
    // Sydney as the default city
    internal var city: String
        get() = prefs.getString("city", "Jaipur, IN")
        set(city) {
            prefs.edit().putString("city", city).commit()
        }

    init {
        prefs = activity.getPreferences(Activity.MODE_PRIVATE)
    }

}