package com.example.payment1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class show_payment_Details : AppCompatActivity() {

    private lateinit var first_name: TextView
    private lateinit var last_name: TextView
    private lateinit var mail: TextView
    private lateinit var phone_number: TextView
    private lateinit var address: TextView


    private var db = Firebase.firestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_payment_details)

        first_name = findViewById(R.id.fname)
        last_name = findViewById(R.id.lname)
        mail = findViewById(R.id.mails)
        phone_number = findViewById(R.id.pnumber)
        address = findViewById(R.id.addres)

        val userId = UUID.randomUUID().toString()
        val ref = db.collection("payment Details").document("92e5739c-58cd-4537-b0d5-8df21420b422")
        ref.get().addOnSuccessListener {
            if (it != null){
                val fname = it.data?.get("first_name")?.toString()
                val lname = it.data?.get("last_name")?.toString()
                val mails = it.data?.get("mail")?.toString()
                val pnumber = it.data?.get("phone_number")?.toString()
                val addres = it.data?.get("address")?.toString()

                first_name.text = fname
                last_name.text = lname
                mail.text = mails
                phone_number.text = pnumber
                address.text = addres

        }

        }
            .addOnFailureListener {
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }
    }
}