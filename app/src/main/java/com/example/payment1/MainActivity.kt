package com.example.payment1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private lateinit var card_number:EditText
    private lateinit var bank_name:EditText
    private lateinit var mobile_number:EditText
    private lateinit var month:EditText
    private lateinit var cvv:EditText
    private lateinit var cancel:Button
    private lateinit var pay:Button
    //deshitha
    private var db = FirebaseFirestore.getInstance();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        card_number = findViewById(R.id.card_number)
        bank_name = findViewById(R.id.bank_name)
        mobile_number = findViewById(R.id.mobile_number)
        month = findViewById(R.id.month)
        cvv = findViewById(R.id.cvv)
        cancel = findViewById(R.id.cancel)
        pay = findViewById(R.id.pay)

        pay.setOnClickListener {
            val Intent = Intent(this,successful_details::class.java)
            startActivity(Intent)
            pay.visibility = View.VISIBLE

            val userId = UUID.randomUUID().toString()

            val card_number_two = card_number.text.toString().trim()
            val bank_name_two = bank_name.text.toString().trim()
            val mobile_number_two = mobile_number.text.toString().trim()
            val month_two = month.text.toString().trim()
            val cvv_two = cvv.text.toString().trim()


            val useMap = hashMapOf(
                "id" to userId,
                "card_number" to card_number_two,
                "bank_name" to bank_name_two,
                "mobile_number" to mobile_number_two,
                "month" to month_two,
                "cvv" to cvv_two
            )


            db.collection("user").document(userId).set(useMap)
                .addOnSuccessListener{
                    Toast.makeText(this, "Successfully Added",Toast.LENGTH_SHORT).show()
                    card_number.text.clear()
                    bank_name.text.clear()
                    mobile_number.text.clear()
                    month.text.clear()
                    cvv.text.clear()
                }
                .addOnFailureListener {
                    val Intent = Intent(this,MainActivity::class.java)
                    startActivity(Intent)
                    Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
                }
        }
    }
}
