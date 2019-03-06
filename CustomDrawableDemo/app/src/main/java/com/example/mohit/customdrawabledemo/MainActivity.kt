package com.example.mohit.customdrawabledemo

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    internal lateinit var mCircle: ExpandingCircleAnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val iv = findViewById<View>(R.id.image) as ImageView
        mCircle = ExpandingCircleAnimationDrawable(200f)
        iv.setImageDrawable(mCircle)


        findViewById<View>(R.id.btnCodeDrawable).setOnClickListener {
            if (mCircle.isRunning) {
                mCircle.stop()
            } else {
                mCircle.start()
            }
        }

    }
}
