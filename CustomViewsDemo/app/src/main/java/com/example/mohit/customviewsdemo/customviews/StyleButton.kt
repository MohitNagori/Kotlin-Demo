package com.example.mohit.customviewsdemo.customviews

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.support.v7.widget.AppCompatButton
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.AttributeSet

import com.example.mohit.customviewsdemo.R

/**
 * Created by Mohit.
 */

class StyleButton : AppCompatButton {

    private var heading: String? = ""
    private var subheading: String? = ""
    private val styleState = false

    constructor(context: Context) : super(context) {
        // TODO Auto-generated constructor stub
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        // TODO Auto-generated constructor stub
        initStyleButton(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        // TODO Auto-generated constructor stub
        initStyleButton(attrs)
    }

    private fun initStyleButton(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.StyleButton)
        heading = a.getString(R.styleable.StyleButton_heading)
        subheading = a.getString(R.styleable.StyleButton_subHeading)
        setCompleteText()
        setStyle()
        a.recycle()
    }

    fun setHeading(heading: String) {
        this.heading = heading
        setCompleteText()
    }

    fun setSubheading(subheading: String) {
        this.subheading = subheading
        setCompleteText()
    }

    private fun setCompleteText() {
        val span1 = SpannableString(heading)
        val span2 = SpannableString(subheading)

        span1.setSpan(ForegroundColorSpan(Color.RED), 0, heading!!.length, 0)
        span2.setSpan(RelativeSizeSpan(0.75f), 0, subheading!!.length, 0)

        text = TextUtils.concat(span1, "\n", span2)
    }

    private fun setStyle() {
        if (styleState)
            setBackgroundResource(R.drawable.rounded_corners)
        else
            setBackgroundColor(Color.WHITE)
    }

}