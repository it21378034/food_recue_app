package com.example.payment1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class once_payment_page : AppCompatActivity() {

    private lateinit var button100: Button
    private lateinit var button200: Button
    private lateinit var button300: Button
    private lateinit var button400: Button
    private lateinit var button500: Button
    private lateinit var button600: Button
    private lateinit var buttonok:Button
    private lateinit var donates:EditText

    private var db = FirebaseFirestore.getInstance();

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_once_payment_page)

        button100 = findViewById(R.id.button1)
        button200 = findViewById(R.id.button2)
        button300 = findViewById(R.id.button3)
        button400 = findViewById(R.id.button4)
        button500 = findViewById(R.id.button5)
        button600 = findViewById(R.id.button6)
        buttonok = findViewById(R.id.ok)
        donates = findViewById(R.id.donate)


        button100.setOnClickListener {
            val prices = findViewById<TextView>(R.id.donate)
            prices.text = "100"

        }
        button200.setOnClickListener {
            val prices = findViewById<TextView>(R.id.donate)
            prices.text = "200"

        }
        button300.setOnClickListener {
            val prices = findViewById<TextView>(R.id.donate)
            prices.text = "300"

        }
        button400.setOnClickListener {
            val prices = findViewById<TextView>(R.id.donate)
            prices.text = "400"

        }
        button500.setOnClickListener {
            val prices = findViewById<TextView>(R.id.donate)
            prices.text = "500"

        }
        button600.setOnClickListener {
            val prices = findViewById<TextView>(R.id.donate)
            prices.text = "600"

        }

        buttonok.setOnClickListener {
            buttonok.visibility = View.VISIBLE

            val userId = UUID.randomUUID().toString()

            val donate = donates.text.toString().trim()

            val useMap = hashMapOf(
                "price" to donate
            )
            db.collection("payment Details").document(userId).set(useMap)
                .addOnSuccessListener {
                    Toast.makeText(this, "Successfully Added", Toast.LENGTH_SHORT).show()
                    donates.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
                }

        }


    }

}


