package com.example.mohit.formdemo

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list.*
import android.content.res.Configuration
import android.support.v7.widget.GridLayoutManager


class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.layoutManager =  GridLayoutManager(this, 2)
        } else {
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
        recyclerView.adapter = ListAdapter(getList())
    }

    private fun getList(): ArrayList<String> {
        var list : ArrayList<String> = ArrayList<String>()

        var i : Int =  50
        while (i-- > 0) {
            list.add("Name " + i)
        }
        return list
    }

}
