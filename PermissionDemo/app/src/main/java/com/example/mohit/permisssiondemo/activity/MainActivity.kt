package com.example.mohit.permisssiondemo.activity

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.Toast

import com.example.mohit.permisssiondemo.R

class MainActivity : AppCompatActivity() {
    private var sentToSettings = false
    private var permissionStatus: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        permissionStatus = getSharedPreferences("permissionStatus", Context.MODE_PRIVATE)

        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //Show Information about why you need the permission
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setTitle("Need Storage Permission")
                    builder.setMessage("This app needs storage permission.")
                    builder.setPositiveButton("Grant") { dialog, which ->
                        dialog.cancel()
                        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), EXTERNAL_STORAGE_PERMISSION_CONSTANT)
                    }
                    builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
                    builder.show()
                } else if (permissionStatus!!.getBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, false)) {
                    //Previously Permission Request was cancelled with 'Dont Ask Again',
                    // Redirect to Settings after showing Information about why you need the permission
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setTitle("Need Storage Permission")
                    builder.setMessage("This app needs storage permission.")
                    builder.setPositiveButton("Grant") { dialog, which ->
                        dialog.cancel()
                        sentToSettings = true
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING)
                        Toast.makeText(baseContext, "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show()
                    }
                    builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
                    builder.show()
                } else {
                    //just request the permission
                    ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), EXTERNAL_STORAGE_PERMISSION_CONSTANT)
                }


                val editor = permissionStatus!!.edit()
                editor.putBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, true)
                editor.commit()


            } else {
                //You already have the permission, just go ahead.
                proceedAfterPermission()
            }
        }

        val btnLaunchMultiplePermission = findViewById<View>(R.id.btnLaunchMultiplePermission) as Button
        btnLaunchMultiplePermission.setOnClickListener {
            val intent = Intent(this@MainActivity, MultiplePermissionsActivity::class.java)
            startActivity(intent)
        }

        val btnLaunchPermissionFragment = findViewById<View>(R.id.btnLaunchPermissionFragment) as Button
        btnLaunchPermissionFragment.setOnClickListener {
            val intent = Intent(this@MainActivity, FragmentPermissionActivity::class.java)
            startActivity(intent)
        }
    }


    private fun proceedAfterPermission() {
        //We've got the permission, now we can proceed further
        Toast.makeText(baseContext, "We got the Storage Permission", Toast.LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == EXTERNAL_STORAGE_PERMISSION_CONSTANT) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //The External Storage Write Permission is granted to you... Continue your left job...
                proceedAfterPermission()
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //Show Information about why you need the permission
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setTitle("Need Storage Permission")
                    builder.setMessage("This app needs storage permission")
                    builder.setPositiveButton("Grant") { dialog, which ->
                        dialog.cancel()


                        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), EXTERNAL_STORAGE_PERMISSION_CONSTANT)
                    }
                    builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
                    builder.show()
                } else {
                    Toast.makeText(baseContext, "Unable to get Permission", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission()
            }
        }
    }


    override fun onPostResume() {
        super.onPostResume()
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission()
            }
        }
    }

    companion object {
        private val EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100
        private val REQUEST_PERMISSION_SETTING = 101
    }
}