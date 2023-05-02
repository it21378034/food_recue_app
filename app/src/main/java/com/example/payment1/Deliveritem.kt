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

class Deliveritem : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etCode : EditText
    private lateinit var etQuantity : EditText
    private lateinit var  btnSUBMIT : Button

    private var db = Firebase.firestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deliveritem)

        etName = findViewById(R.id.textView7)
        etCode = findViewById(R.id.textView8)
        etQuantity = findViewById(R.id.textView9)
        btnSUBMIT = findViewById(R.id.button2)

        btnSUBMIT.setOnClickListener {

            val userId = UUID.randomUUID().toString()

            val sName = etName.text.toString().trim()
            val sCode = etCode.text.toString().trim()
            val sQuantity = etQuantity.text.toString().trim()

            val userMap = hashMapOf(
                "textView7" to sName,
                "textView8" to sCode,
                "textView9" to sQuantity
            )


            db.collection("kavidu").document(userId).set(userMap)
                .addOnSuccessListener{
                    Toast.makeText(this,"Successfully Added!",Toast.LENGTH_SHORT).show()
                    etName.text.clear()
                    etCode.text.clear()
                    etQuantity.text.clear()

                }
                .addOnFailureListener{
                    Toast.makeText(this,"Failed!",Toast.LENGTH_SHORT).show()
                }



        }
    }
}
