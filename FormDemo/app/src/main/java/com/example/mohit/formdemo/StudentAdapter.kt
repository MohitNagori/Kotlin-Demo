package com.example.mohit.formdemo

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.student_list_item.view.*

import java.util.ArrayList

/**
 * Created by Mohit on 4/19/2018.
 */

class StudentAdapter(internal var activity: Activity, internal var stuList: ArrayList<Student>) : BaseAdapter() {

    override fun getCount(): Int {
        return stuList.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view: View = inflater.inflate(R.layout.student_list_item, null)

        var student :  Student = stuList.get(position)
        view.name.text = student.fname + " " + student.lname
        view.gender.text = student.gender
        view.clas.text = student.clas + "-" + student.section
        view.games.text = TextUtils.join(", ", student.games)

        return view
    }
}
