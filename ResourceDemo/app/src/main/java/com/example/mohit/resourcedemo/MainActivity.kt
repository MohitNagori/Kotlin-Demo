package com.example.mohit.resourcedemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.DividerItemDecoration


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var recyclerView : RecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        var layoutManager : LinearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = ListAdapter(getList())

        val dividerItemDecoration = DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation())
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun getList(): ArrayList<Employee> {
        var list : ArrayList<Employee> = ArrayList<Employee>()

        list.add(Employee("Mohit", "Nagori", "Software Developer", "Metacube", "+91 9784323158 | Skype: 9784323158"))
        list.add(Employee("Akriti", "Sondhi", "Software Developer", "Metacube", "+91 9988776655 | Skype: @akriti"))
        list.add(Employee("Harshita", "Ahuja", "Software Developer", "Metacube", "+91 8877665544 | Skype: @harshita"))
        list.add(Employee("Varun", "Shrivastava", "Software Developer", "Metacube", "+91 7766554433 | Skype: @varun"))

        return list
    }

}
