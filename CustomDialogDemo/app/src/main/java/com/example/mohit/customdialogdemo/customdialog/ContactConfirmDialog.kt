package com.example.mohit.customdialogdemo.customdialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.widget.*

import com.example.mohit.customdialogdemo.R


/**
 * Created by Mohit.
 */

class ContactConfirmDialog(private val activity: Activity, private val bundle: Bundle) : Dialog(activity), View.OnClickListener {

    lateinit var name : EditText
    lateinit var number : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setCancelable(false)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.contact_confirm_dialog)

        name = findViewById<EditText>(R.id.name)
        number = findViewById<EditText>(R.id.number)

        findViewById<ImageView>(R.id.close).setOnClickListener(this)
        findViewById<Button>(R.id.confirm).setOnClickListener(this)

        name.setText(bundle.getString(CUSTOMDIALOG_NAME, ""))
        number.setText(bundle.getString(CUSTOMDIALOG_NUMBER, ""))

        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val widthLcl = (displayMetrics.widthPixels * 0.8f).toInt()

        var rootView : View = findViewById(R.id.root)
        val params = rootView.layoutParams as FrameLayout.LayoutParams
        params.width = widthLcl
        rootView.layoutParams = params
        val window = window
        window!!.setBackgroundDrawableResource(R.drawable.grey_rounded_bckground)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.close -> {
                dismiss()
            }
            R.id.confirm -> {
                if (validate()) {
                    dismiss()
                    Toast.makeText(activity, "Name - " + name.getText().toString()
                            + "\nNumber - " + number.getText().toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun validate(): Boolean {
        if (name.getText().toString().isEmpty() || number.getText().toString().isEmpty()) {
            if (name.getText().toString().isEmpty()) {
                name.setError("Please Fill Name")
            } else {
                name.setError(null)
            }

            if (number.getText().toString().isEmpty()) {
                number.setError("Please Fill Number")
            } else {
                number.setError(null)
            }

            return false
        }
        return true
    }

    companion object {
        var CUSTOMDIALOG_NAME = "customdialog_name"
        var CUSTOMDIALOG_NUMBER = "customdialog_number"
    }
}