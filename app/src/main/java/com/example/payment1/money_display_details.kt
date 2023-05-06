package com.example.payment1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class money_display_details : AppCompatActivity() {

    private lateinit var DMName: TextView
    private lateinit var DMAddress: TextView
    private lateinit var DMNic: TextView
    private lateinit var DMAccNum: TextView

    private var db = Firebase.firestore
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money_display_details)

        DMName = findViewById(R.id.etuMname)
        DMAddress = findViewById(R.id.etuMaddress)
        DMNic = findViewById(R.id.etuMnic)
        DMAccNum = findViewById(R.id.etuMPhone)

        val adminId = UUID.randomUUID().toString()
        val ref = db.collection("MoneyReciver").document("36e96e50-f5e6-404b-a4dd-faeaa96222a7")
        ref.get().addOnSuccessListener {
            if(it != null){
                val etMRecName = it.data?.get("Name")?.toString()
                val etMRecAddress = it.data?.get("Address")?.toString()
                val etMRecNic = it.data?.get("Nic")?.toString()
                val etMRecAccNum = it.data?.get("AccountNum")?.toString()

                DMName.text =etMRecName
                DMAddress.text =etMRecAddress
                DMNic.text =etMRecNic
                DMAccNum.text =etMRecAccNum
            }
        }
            .addOnFailureListener {
                Toast.makeText(this, "Failed!!" , Toast.LENGTH_SHORT).show()
            }

    }
}