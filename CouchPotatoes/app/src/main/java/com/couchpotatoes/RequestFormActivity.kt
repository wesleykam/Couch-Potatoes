package com.couchpotatoes

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.NumberPicker
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

        val hourPicker = findViewById<NumberPicker>(R.id.hourPicker)
        val dayPicker = findViewById<NumberPicker>(R.id.dayPicker)

        hourPicker.maxValue = 23
        hourPicker.minValue = 1

        dayPicker.maxValue = 7
        dayPicker.minValue = 0
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
        val durationEditHours = findViewById<NumberPicker>(R.id.hourPicker)
        val durationHours = durationEditHours.value
        val durationEditDays = findViewById<NumberPicker>(R.id.dayPicker)
        val durationDays = durationEditDays.value

        val expirationTime = System.currentTimeMillis() + (durationHours * 60 * 60 * 1000) + (durationDays * 24 * 60 * 60 * 1000)

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
            expirationTime,
            "pending")

        database.child("jobs").child(jobId).setValue(job)
    }
}