package com.example.mohit.permisssiondemo.fragment

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


import com.example.mohit.permisssiondemo.R


/**
 * A placeholder fragment containing a simple rootView.
 */
class PermissionsFragment : Fragment() {
    private var rootView: View? = null
    private var txtPermissions: TextView? = null
    private var btnCheckPermissions: Button? = null


    private var permissionStatus: SharedPreferences? = null
    private var sentToSettings = false


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater!!.inflate(R.layout.fragment_permissions, container, false)
        return rootView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        permissionStatus = activity.getSharedPreferences("permissionStatus", Context.MODE_PRIVATE)


        if (null != rootView) {
            txtPermissions = rootView!!.findViewById<View>(R.id.txtPermissions) as TextView
            btnCheckPermissions = rootView!!.findViewById<View>(R.id.btnCheckPermissions) as Button


            btnCheckPermissions!!.setOnClickListener {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_PHONE_STATE)) {
                        //Show Information about why you need the permission
                        val builder = AlertDialog.Builder(activity)
                        builder.setTitle("Need Permission")
                        builder.setMessage("This app needs phone permission.")
                        builder.setPositiveButton("Grant") { dialog, which ->
                            dialog.cancel()
                            requestPermissions(arrayOf(Manifest.permission.READ_PHONE_STATE), PERMISSION_CALLBACK_CONSTANT)
                        }
                        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
                        builder.show()
                    } else if (permissionStatus!!.getBoolean(Manifest.permission.READ_PHONE_STATE, false)) {
                        //Previously Permission Request was cancelled with 'Dont Ask Again',
                        // Redirect to Settings after showing Information about why you need the permission
                        val builder = AlertDialog.Builder(activity)
                        builder.setTitle("Need Permission")
                        builder.setMessage("This app needs storage permission.")
                        builder.setPositiveButton("Grant") { dialog, which ->
                            dialog.cancel()
                            sentToSettings = true
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri = Uri.fromParts("package", activity.packageName, null)
                            intent.data = uri
                            startActivityForResult(intent, REQUEST_PERMISSION_SETTING)
                            Toast.makeText(activity, "Go to Permissions to Grant Phone", Toast.LENGTH_LONG).show()
                        }
                        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
                        builder.show()
                    } else {
                        //just request the permission
                        requestPermissions(arrayOf(Manifest.permission.READ_PHONE_STATE), PERMISSION_CALLBACK_CONSTANT)
                    }
                    txtPermissions!!.text = "Permissions Required"


                    val editor = permissionStatus!!.edit()
                    editor.putBoolean(Manifest.permission.READ_PHONE_STATE, true)
                    editor.commit()
                } else {
                    //You already have the permission, just go ahead.
                    proceedAfterPermission()
                }
            }
        }
    }


    private fun proceedAfterPermission() {
        txtPermissions!!.text = "We've got all permissions"
        Toast.makeText(activity, "We got All Permissions", Toast.LENGTH_LONG).show()
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
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_PHONE_STATE)) {
                txtPermissions!!.text = "Permissions Required"
                val builder = AlertDialog.Builder(activity)
                builder.setTitle("Need Storage Permission")
                builder.setMessage("This app needs phone permission.")
                builder.setPositiveButton("Grant") { dialog, which ->
                    dialog.cancel()
                    requestPermissions(arrayOf(Manifest.permission.READ_PHONE_STATE), PERMISSION_CALLBACK_CONSTANT)
                }
                builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
                builder.show()
            } else {
                Toast.makeText(activity, "Unable to get Permission", Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission()
            }
        }
    }


    override fun onResume() {
        super.onResume()


        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission()
            }
        }
    }

    companion object {


        private val PERMISSION_CALLBACK_CONSTANT = 101
        private val REQUEST_PERMISSION_SETTING = 102
    }
}