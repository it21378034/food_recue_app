package com.example.payment1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.UUID

class food_display_details : AppCompatActivity() {

    private lateinit var DName: TextView
    private lateinit var DAddress: TextView
    private lateinit var DNic: TextView
    private lateinit var DMobNum: TextView


private var db = Firebase.firestore
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_display_details)

        DName = findViewById(R.id.etufname)
        DAddress = findViewById(R.id.etufaddress)
        DNic = findViewById(R.id.etufnic)
        DMobNum = findViewById(R.id.etufPhone)

        val adminId = UUID.randomUUID().toString()
                val ref = db.collection("nethmini").document("98c5b089-4b99-4b57-9ca8-832bd74d4cdd")
        ref.get().addOnSuccessListener {
            if(it != null){
                val edufname = it.data?.get("Name")?.toString()
                val edufaddress = it.data?.get("Address")?.toString()
                val edufnic = it.data?.get("Nic")?.toString()
                val etMRecAccNum = it.data?.get("Num")?.toString()

                DName.text =edufname
                DAddress.text =edufaddress
                DNic.text =edufnic
                DMobNum.text =etMRecAccNum
            }
        }
            .addOnFailureListener {
                Toast.makeText(this, "Failed!!" , Toast.LENGTH_SHORT).show()
            }
    }
}