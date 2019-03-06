package com.example.mohit.formdemo

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_add.*



class AddFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addStudent.setOnClickListener(View.OnClickListener {
            if (validate()) {

                var student: Student = Student()
                student.fname = firstname.text.toString()
                student.lname = lastname.text.toString()
                student.clas = clas.text.toString()
                student.section = section.text.toString()
                student.games = games.text.toString().split(", ").toTypedArray()
                var genderCheck = gender.checkedRadioButtonId
                if (genderCheck == male.id) {
                    student.gender = "Male"
                } else {
                    student.gender = "Female"
                }
                if (activity is MainActivity) {
                    var activity: MainActivity = activity as MainActivity
                    activity.updateList(student)
                    clearData()
                }
            }
        })
        clas.setOnClickListener(View.OnClickListener {
            val clasList = arrayOf("I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII")
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Select Class")
            builder.setSingleChoiceItems(clasList, -1
            ) { dialog, item ->
                clas.setText(clasList[item])
                dialog.dismiss()
            }.create().show()
        })
        section.setOnClickListener(View.OnClickListener {
            val sectionList = arrayOf("A", "B", "C", "D")
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Select Section")
            builder.setSingleChoiceItems(sectionList, -1
            ) { dialog, item ->
                section.setText(sectionList[item])
                dialog.dismiss()
            }.create().show()
        })
        games.setOnClickListener(View.OnClickListener {
            val builder = AlertDialog.Builder(activity)

            // String array for alert dialog multi choice items
            val gamesArray = arrayOf("Badminton", "Cricket", "Table Tennis", "Hockey", "Chess")

            // Boolean array for initial selected items
            val checkedColors = booleanArrayOf(false, // Red
                    false, // Green
                    false, // Blue
                    false, // Purple
                    false // Olive
            )

            builder.setMultiChoiceItems(gamesArray, checkedColors) { dialog, which, isChecked ->
                // Update the current focused item's checked status
                checkedColors[which] = isChecked
            }

            // Specify the dialog is not cancelable
            builder.setCancelable(false)

            // Set a title for alert dialog
            builder.setTitle("Choose games")

            // Set the positive/yes button click listener
            builder.setPositiveButton("OK") { dialog, which ->
                // Do something when click positive button
                var gamesList : ArrayList<String> = ArrayList()
                for (i in checkedColors.indices) {
                    val checked = checkedColors[i]
                    if (checked) {
                        gamesList.add(gamesArray[i])
                    }
                }
                games.setText(TextUtils.join(", ", gamesList))
            }.create().show()
        })

    }

    private fun clearData() {
        firstname.setText("")
        lastname.setText("")
        clas.setText("")
        section.setText("")
        games.setText("")
    }

    private fun validate(): Boolean {
        if (firstname.text.toString().isEmpty() ||
                lastname.text.toString().isEmpty() ||
                clas.text.toString().isEmpty() ||
                section.text.toString().isEmpty() ||
                games.text.toString().isEmpty()) {
            Toast.makeText(context, "Please fill all the details", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

}