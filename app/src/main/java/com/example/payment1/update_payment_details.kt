package com.example.payment1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class update_payment_details : AppCompatActivity() {



    private lateinit var first_name: EditText
    private lateinit var last_name: EditText
    private lateinit var mail: EditText
    private lateinit var phone_number: EditText
    private lateinit var address: EditText
    private lateinit var update: Button

    private var db = FirebaseFirestore.getInstance();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_payment_details)


        first_name = findViewById(R.id.first_name)
        last_name = findViewById(R.id.last_name)
        mail = findViewById(R.id.mail)
        phone_number = findViewById(R.id.phone_number)
        address = findViewById(R.id.address)
        update = findViewById(R.id.update)


        setData()

        update.setOnClickListener {
            val sfirst_name = first_name.text.toString()
            val slast_name = last_name.text.toString()
            val smail = mail.text.toString()
            val sphone_number = phone_number.text.toString()
            val saddress = address.text.toString()

            val updateMap = mapOf(
                "First_name" to sfirst_name,
                "Last_name" to slast_name,
                "mail" to smail,
                "phone_number" to sphone_number,
                "address" to saddress
            )
            val dataId = UUID.randomUUID().toString()
            db.collection("payment Details").document(dataId).update(updateMap)


            Toast.makeText(this,"successfully Edited",Toast.LENGTH_SHORT).show()
        }

    }



    private fun setData() {
        val userId = UUID.randomUUID().toString()
        val ref = db.collection("payment Details").document(userId)
        ref.get().addOnSuccessListener {
            if (it != null){
                val first_name = it.data?.get("First_name")?.toString()
                val last_name = it.data?.get("Last_name")?.toString()
                val mail = it.data?.get("mail")?.toString()
                val phone_number = it.data?.get("phone_number")?.toString()
                val address = it.data?.get("address")?.toString()
            }
        }
            .addOnFailureListener {
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }
    }
}