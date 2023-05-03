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
    private lateinit var addres: TextView


    private var db = Firebase.firestore


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_payment_details)

        first_name = findViewById(R.id.first_name)
        last_name = findViewById(R.id.last_name)
        mail = findViewById(R.id.mail)
        phone_number = findViewById(R.id.phone_number)
        addres = findViewById(R.id.addres)

        val userId = UUID.randomUUID().toString()
        val ref = db.collection("payment Details").document(userId)
        ref.get().addOnSuccessListener {
            if (it != null){
                val first_name1 = it.data?.get("first_name")?.toString()
                val last_name1 = it.data?.get("last_name")?.toString()
                val mail1 = it.data?.get("mail")?.toString()
                val phone_number1 = it.data?.get("phone_number")?.toString()
                val addres1 = it.data?.get("addres")?.toString()


                first_name.text = first_name1
                last_name.text = last_name1
                mail.text = mail1
                phone_number.text = phone_number1
                addres.text = addres1

        }

        }
            .addOnFailureListener {
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }
    }
}