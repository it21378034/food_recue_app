package com.example.payment1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.UUID

class Detailsadd : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etID : EditText
    private lateinit var etContactNumber : EditText
    private lateinit var etEmail : EditText
    private lateinit var  btnSUBMIT : Button

    private var db = Firebase.firestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailsadd)

        etName = findViewById(R.id.name)
        etID = findViewById(R.id.idc)
        etContactNumber = findViewById(R.id.contact)
        etEmail = findViewById(R.id.email)
        btnSUBMIT = findViewById(R.id.button)

        btnSUBMIT.setOnClickListener {

            val userId = UUID.randomUUID().toString()

            val sName = etName.text.toString().trim()
            val sID = etID.text.toString().trim()
            val sContactNumber = etContactNumber.text.toString().trim()
            val sEmail = etEmail.text.toString().trim()

            val userMap = hashMapOf(
                "id" to userId,
                "name" to sName,
                "idc" to sID,
                "contact" to sContactNumber,
                "email" to sEmail
            )


            db.collection("detailsadd").document(userId).set(userMap)
                .addOnSuccessListener{
                    Toast.makeText(this,"Successfully Added!",Toast.LENGTH_SHORT).show()
                    etName.text.clear()
                    etID.text.clear()
                    etContactNumber.text.clear()
                    etEmail.text.clear()
                }
                .addOnFailureListener{
                    Toast.makeText(this,"Failed!",Toast.LENGTH_SHORT).show()
                }



        }
    }
}
