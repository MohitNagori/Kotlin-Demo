package com.example.mohit.lifecycledemo

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class BlankFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onStart() {
        super.onStart()
        Log.d("1234", "Called on Fragment Start")
    }

    override fun onResume() {
        super.onResume()
        Log.d("1234", "Called on Fragment Resume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("1234", "Called on Fragment Pause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("1234", "Called on Fragment Stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("1234", "Called on Fragment Destroy")
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.d("1234", "Called on Fragment Start")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("1234", "Called on Fragment Start")
    }
}