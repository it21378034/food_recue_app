package com.example.payment1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class money_update_details : AppCompatActivity() {

    private lateinit var umName: TextView
    private lateinit var umAddress: TextView
    private lateinit var umNic: TextView
    private lateinit var umAccNum: TextView
    private lateinit var umpdate: Button

    private var db = Firebase.firestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money_update_details)

        umName = findViewById(R.id.etuMname)
        umAddress = findViewById(R.id.etuMaddress)
        umNic = findViewById(R.id.etuMnic)
        umAccNum = findViewById(R.id.etuMPhone)
        umpdate = findViewById(R.id.btnupdate)

        setData()

        umpdate.setOnClickListener {
            val Name = umName.text.toString()
            val Address = umAddress.text.toString()
            val Nic = umNic.text.toString()
            val AccountNum = umAccNum.text.toString()

            val updateMap = mapOf(
                "Name" to Name,
                "Address" to Address,
                "Nic" to Nic,
                "AccountNum" to AccountNum

            )

            val adminId = UUID.randomUUID().toString()
            db.collection("nethmini").document("36e96e50-f5e6-404b-a4dd-faeaa96222a7").update(updateMap)

            Toast.makeText(this, "Successfully Edited!", Toast.LENGTH_SHORT).show()

        }

    }

    private fun setData() {
        val adminId = UUID.randomUUID().toString()
        val ref = db.collection("MoneyReciver").document("36e96e50-f5e6-404b-a4dd-faeaa96222a7")
        ref.get().addOnSuccessListener {
            if(it != null){
                val etuMname = it.data?.get("Name")?.toString()
                val etuMaddress = it.data?.get("Address")?.toString()
                val etuMnic = it.data?.get("Nic")?.toString()
                val etuMAccNum = it.data?.get("AccountNum")?.toString()

                umName.text = (etuMname)
                umAddress.text=(etuMaddress)
                umNic.text = (etuMnic)
                umAccNum.text = (etuMAccNum)
            }
        }
            .addOnFailureListener {
                Toast.makeText(this, "Failed!!" , Toast.LENGTH_SHORT).show()
            }

    }
}