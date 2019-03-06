package com.example.mohit.lifecycledemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        findViewById<TextView>(R.id.firstActivity).setOnClickListener {
            val intent = Intent(this@SecondActivity, MainActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.secondActivity).setOnClickListener {
            val intent = Intent(this@SecondActivity, SecondActivity::class.java)
            startActivity(intent)
        }
    }
}
