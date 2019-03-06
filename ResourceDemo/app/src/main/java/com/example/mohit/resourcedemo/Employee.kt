package com.example.mohit.resourcedemo

/**
 * Created by Mohit on 4/21/2018.
 */

class Employee {

    var fname: String? = null
    var lname: String? = null
    var designation: String? = null
    var company: String? = null
    var contact: String? = null

    constructor(fname: String?, lname: String?, designation: String?, company: String?, contact: String?) {
        this.fname = fname
        this.lname = lname
        this.designation = designation
        this.company = company
        this.contact = contact
    }

    constructor()
}
