package com.example.payment1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class successful_details : AppCompatActivity() {


    private lateinit var bankname: TextView
    private lateinit var mobilenumber: TextView
    private lateinit var okbtn: Button

    private var db = Firebase.firestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_successful_details)


        bankname = findViewById(R.id.bname)
        mobilenumber = findViewById(R.id.mobilenumber)
        okbtn = findViewById(R.id.ok)


        val userId = UUID.randomUUID().toString()
        val ref = db.collection("user").document("5752dcdb-3d7f-4b25-b68f-5d9fd0681864")
        ref.get().addOnSuccessListener {
            if (it != null) {
                val bname = it.data?.get("bank_name")?.toString()
                val mnumber = it.data?.get("mobile_number")?.toString()


                bankname.text = bname
                mobilenumber.text = mnumber
            }

        }
            .addOnFailureListener {
                Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
            }

        okbtn.setOnClickListener {
            val Intent = Intent(this,Button_Display_page::class.java)
            startActivity(Intent)
        }

    }
}