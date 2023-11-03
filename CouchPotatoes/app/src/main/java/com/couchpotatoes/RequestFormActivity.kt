package com.couchpotatoes

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.couchpotatoes.classes.Job
import com.couchpotatoes.classes.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import java.util.UUID


class RequestFormActivity : BaseActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_form)

        auth = FirebaseAuth.getInstance()

        createNavMenu(R.id.my_toolbar, this, auth)
    }

    fun submitbuttonHandler(view: View) {
        //Decide what happens when the user clicks the submit button
        database = Firebase.database.reference

        val whatEditText = findViewById<View>(R.id.what) as EditText
        val what = whatEditText.text.toString()
        val whereEditText = findViewById<View>(R.id.where) as EditText
        val where = whereEditText.text.toString()
        val costEditText = findViewById<View>(R.id.cost) as EditText
        val cost = costEditText.text.toString()
        val addressEditText = findViewById<View>(R.id.address) as EditText
        val address = addressEditText.text.toString()

        // switch to better system later
        val jobId = UUID.randomUUID().toString()

        // create job
        val job = Job(
            jobId,
            FirebaseAuth.getInstance().currentUser!!.displayName,
            FirebaseAuth.getInstance().currentUser!!.email,
            what,
            cost,
            where,
            address,
            "pending")

        database.child("jobs").child(jobId).setValue(job)
    }
}