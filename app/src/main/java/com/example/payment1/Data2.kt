package com.example.payment1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.UUID

class Data2 : AppCompatActivity() {

    private lateinit var DName: TextView
    private lateinit var DCode: TextView
    private lateinit var DQuantity: TextView


    private var db = Firebase.firestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data2)

        DName = findViewById(R.id.name)
        DCode = findViewById(R.id.code)
        DQuantity = findViewById(R.id.quantity)


        val data2ID = UUID.randomUUID().toString()
        val ref = db.collection("kavidu").document("5958dda0-cec6-478c-8b0a-1f2736bbdcd3")
        ref.get().addOnSuccessListener{
            if (it!=null){
                val etRecName = it.data?.get("textView7")?.toString()
                val etRecCode = it.data?.get("textView8")?.toString()
                val etRecQuantity = it.data?.get("textView9")?.toString()


                DName.text = etRecName
                DCode.text = etRecCode
                DQuantity.text = etRecQuantity

            }
        }
            .addOnFailureListener{
                Toast.makeText(this,"Failed!",Toast.LENGTH_SHORT).show()

            }

    }
}