package com.example.mohit.permisssiondemo.activity

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import com.example.mohit.permisssiondemo.R

class MultiplePermissionsActivity : AppCompatActivity() {
    internal var permissionsRequired = arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    private var txtPermissions: TextView? = null
    private var btnCheckPermissions: Button? = null
    private var permissionStatus: SharedPreferences? = null
    private var sentToSettings = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiple_permissions)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        permissionStatus = getSharedPreferences("permissionStatus", Context.MODE_PRIVATE)

        txtPermissions = findViewById<View>(R.id.txtPermissions) as TextView
        btnCheckPermissions = findViewById<View>(R.id.btnCheckPermissions) as Button

        btnCheckPermissions!!.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this@MultiplePermissionsActivity, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this@MultiplePermissionsActivity, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this@MultiplePermissionsActivity, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this@MultiplePermissionsActivity, permissionsRequired[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this@MultiplePermissionsActivity, permissionsRequired[1])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this@MultiplePermissionsActivity, permissionsRequired[2])) {
                    //Show Information about why you need the permission
                    val builder = AlertDialog.Builder(this@MultiplePermissionsActivity)
                    builder.setTitle("Need Multiple Permissions")
                    builder.setMessage("This app needs Camera and Location permissions.")
                    builder.setPositiveButton("Grant") { dialog, which ->
                        dialog.cancel()
                        ActivityCompat.requestPermissions(this@MultiplePermissionsActivity, permissionsRequired, PERMISSION_CALLBACK_CONSTANT)
                    }
                    builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
                    builder.show()
                } else if (permissionStatus!!.getBoolean(permissionsRequired[0], false)) {
                    //Previously Permission Request was cancelled with 'Dont Ask Again',
                    // Redirect to Settings after showing Information about why you need the permission
                    val builder = AlertDialog.Builder(this@MultiplePermissionsActivity)
                    builder.setTitle("Need Multiple Permissions")
                    builder.setMessage("This app needs Camera and Location permissions.")
                    builder.setPositiveButton("Grant") { dialog, which ->
                        dialog.cancel()
                        sentToSettings = true
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING)
                        Toast.makeText(baseContext, "Go to Permissions to Grant  Camera and Location", Toast.LENGTH_LONG).show()
                    }
                    builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
                    builder.show()
                } else {
                    //just request the permission
                    ActivityCompat.requestPermissions(this@MultiplePermissionsActivity, permissionsRequired, PERMISSION_CALLBACK_CONSTANT)
                }

                txtPermissions!!.text = "Permissions Required"

                val editor = permissionStatus!!.edit()
                editor.putBoolean(permissionsRequired[0], true)
                editor.commit()
            } else {
                //You already have the permission, just go ahead.
                proceedAfterPermission()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            //check if all permissions are granted
            var allgranted = false
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true
                } else {
                    allgranted = false
                    break
                }
            }

            if (allgranted) {
                proceedAfterPermission()
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this@MultiplePermissionsActivity, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this@MultiplePermissionsActivity, permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this@MultiplePermissionsActivity, permissionsRequired[2])) {
                txtPermissions!!.text = "Permissions Required"
                val builder = AlertDialog.Builder(this@MultiplePermissionsActivity)
                builder.setTitle("Need Multiple Permissions")
                builder.setMessage("This app needs Camera and Location permissions.")
                builder.setPositiveButton("Grant") { dialog, which ->
                    dialog.cancel()
                    ActivityCompat.requestPermissions(this@MultiplePermissionsActivity, permissionsRequired, PERMISSION_CALLBACK_CONSTANT)
                }
                builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
                builder.show()
            } else {
                Toast.makeText(baseContext, "Unable to get Permission", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(this@MultiplePermissionsActivity, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission()
            }
        }
    }

    private fun proceedAfterPermission() {
        txtPermissions!!.text = "We've got all permissions"
        Toast.makeText(baseContext, "We got All Permissions", Toast.LENGTH_LONG).show()
    }

    override fun onPostResume() {
        super.onPostResume()
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(this@MultiplePermissionsActivity, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission()
            }
        }
    }

    companion object {
        private val PERMISSION_CALLBACK_CONSTANT = 100
        private val REQUEST_PERMISSION_SETTING = 101
    }
}