package com.example.mohit.customviewsdemo.customviews

import java.text.SimpleDateFormat
import java.util.Calendar

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet

import com.example.mohit.customviewsdemo.R

class DateView : AppCompatTextView {
    private var delimiter: String? = null
    private var isFancyText: Boolean = false

    constructor(context: Context) : super(context) {
        setDate()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.DateView)
        val N = a.indexCount
        for (i in 0..N) {
            val attr = a.getIndex(i)
            when (attr) {
                R.styleable.DateView_delimiter -> {
                    delimiter = a.getString(attr)
                    setDate()
                }

                R.styleable.DateView_isFancyText -> {
                    isFancyText = a.getBoolean(attr, false)
                    fancyText()
                }
            }
        }
        a.recycle()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        setDate()
    }

    private fun setDate() {
        val dateFormat = SimpleDateFormat("yyyy" + delimiter + "MM" + delimiter + "dd")
        val today = dateFormat.format(Calendar.getInstance().time)
        text = today  // self = DateView = subclass of TextView
    }

    private fun fancyText() {
        if (this.isFancyText) {
            setShadowLayer(9f, 1f, 1f, Color.rgb(44, 44, 40))
        } else {
            setShadowLayer(0f, 0f, 0f, Color.rgb(0, 0, 0))
        }
    }

    fun setDelimiter(delimiter: String) {
        this.delimiter = delimiter
        setDate()
    }

    fun setFancyText(isFancyText: Boolean) {
        this.isFancyText = isFancyText
        fancyText()
    }
}