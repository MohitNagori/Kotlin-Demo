package com.example.mohit.resourcedemo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by Mohit on 4/20/2018.
 */
class ListAdapter(private val myDataset: ArrayList<Employee>) :
        RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ListAdapter.ViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false) as View
        // set the view's size, margins, paddings and layout parameters

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        var employee : Employee = myDataset.get(position)
        holder.view.findViewById<TextView>(R.id.image).setText("" + employee.fname?.get(0) + employee.lname?.get(0))
        holder.view.findViewById<TextView>(R.id.name).setText("" + employee.fname + employee.lname)
        holder.view.findViewById<TextView>(R.id.designation).setText("" + employee.designation)
        holder.view.findViewById<TextView>(R.id.companyName).setText("" + employee.company)
        holder.view.findViewById<TextView>(R.id.contact).setText("" + employee.contact)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}