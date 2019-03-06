package com.example.mohit.permisssiondemo.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View

import com.example.mohit.permisssiondemo.fragment.PermissionsFragment
import com.example.mohit.permisssiondemo.R

class FragmentPermissionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_permission)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.flFragments, PermissionsFragment())
        transaction.commit()
        manager.executePendingTransactions()
    }
}
