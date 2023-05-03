package com.example.payment1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import java.net.Inet4Address
import java.util.*

class payment_details : AppCompatActivity() {

    private lateinit var first_name:EditText
    private lateinit var last_name:EditText
    private lateinit var mail:EditText
    private lateinit var phone_number:EditText
    private lateinit var address: EditText
    private lateinit var next:Button

    private var db = FirebaseFirestore.getInstance();


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_details)


        first_name = findViewById(R.id.first_name)
        last_name = findViewById(R.id.last_name)
        mail = findViewById(R.id.mail)
        phone_number = findViewById(R.id.phone_number)
        address = findViewById(R.id.address)
        next = findViewById(R.id.next)

        next.setOnClickListener {
            next.visibility = View.VISIBLE

            val next = findViewById<Button>(R.id.next)
            next.setOnClickListener {
                val Intent =Intent(this,MainActivity::class.java)
                startActivity(Intent)
            }

            val userId = UUID.randomUUID().toString()

            val first_name_two = first_name.text.toString().trim()
            val last_name_two = last_name.text.toString().trim()
            val mail_two = mail.text.toString().trim()
            val phone_number_two = mail.text.toString().trim()
            var address_two = address.text.toString().trim()


            val useMap = hashMapOf(
                "id" to userId,
                "First name" to first_name_two,
                "Last name" to last_name_two,
                "mail" to mail_two,
                "phone number" to phone_number_two,
                "addres" to address_two
            )

            db.collection("payment Details").document(userId).set(useMap)
                .addOnSuccessListener {
                    Toast.makeText(this,"Successfully Added",Toast.LENGTH_SHORT).show()
                    first_name.text.clear()
                    last_name.text.clear()
                    mail.text.clear()
                    phone_number.text.clear()
                    address.text.clear()

                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
        }


    }
}