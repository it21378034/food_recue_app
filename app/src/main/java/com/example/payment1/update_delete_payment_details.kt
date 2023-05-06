package com.example.payment1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class update_delete_payment_details : AppCompatActivity() {

    private lateinit var first_name1: EditText
    private lateinit var last_name1: EditText
    private lateinit var mail1: EditText
    private lateinit var phone_number1: EditText
    private lateinit var address1: EditText
    private lateinit var update: Button

    private var db = Firebase.firestore


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete_payment_details)

        first_name1 = findViewById(R.id.upfname)
        last_name1 = findViewById(R.id.uplname)
        mail1 = findViewById(R.id.upmail)
        phone_number1 = findViewById(R.id.uppnumber)
        address1 = findViewById(R.id.upaddress)
        update = findViewById(R.id.updat)

        setData()

        update.setOnClickListener {
            val first_name = first_name1.text.toString()
            val last_name = last_name1.text.toString()
            val mail = mail1.text.toString()
            val phone_number = phone_number1.text.toString()
            val address = address1.text.toString()

            val updateMap = mapOf(
                "first_name" to first_name,
                "last_name" to last_name,
                "mail" to mail,
                "phone_number" to phone_number,
                "address" to address
            )

            val userId = UUID.randomUUID().toString()
            db.collection("payment Details").document("92e5739c-58cd-4537-b0d5-8df21420b422").update(updateMap)

            Toast.makeText(this,"Successful Edited",Toast.LENGTH_SHORT).show()
        }

    }

    private fun setData() {
        val userId = UUID.randomUUID().toString()
        val ref = db.collection("payment Details").document("92e5739c-58cd-4537-b0d5-8df21420b422")
        ref.get().addOnSuccessListener {
            if (it != null) {
                val upfname = it.data?.get("first_name")?.toString()
                val uplname = it.data?.get("last_name")?.toString()
                val upmail = it.data?.get("mail")?.toString()
                val uppnumber = it.data?.get("phone_number")?.toString()
                val upaddress = it.data?.get("address")?.toString()

                first_name1.setText(upfname)
                last_name1.setText(uplname)
                mail1.setText(upmail)
                phone_number1.setText(uppnumber)
                address1.setText(upaddress)

            }

        }
            .addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }

    }
}