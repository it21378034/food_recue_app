package com.example.payment1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class food_update_data : AppCompatActivity() {

    private lateinit var uName: TextView
    private lateinit var uAddress: TextView
    private lateinit var uNic: TextView
    private lateinit var uMobNum: TextView
    private lateinit var update: Button

    private var db = Firebase.firestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_update_data)

        uName = findViewById(R.id.etuMname)
        uAddress = findViewById(R.id.etuMaddress)
        uNic = findViewById(R.id.etuMnic)
        uMobNum = findViewById(R.id.etuMPhone)
        update = findViewById(R.id.btnupdate)

        setData()

        update.setOnClickListener {
            val Name = uName.text.toString()
            val Address = uAddress.text.toString()
            val Nic = uNic.text.toString()
            val Num = uMobNum.text.toString()

            val updateMap = mapOf(
                "Name" to Name,
                "Address" to Address,
                "Nic" to Nic,
                "Num" to Num

            )

            val adminId = UUID.randomUUID().toString()
            db.collection("nethmini").document("98c5b089-4b99-4b57-9ca8-832bd74d4cdd").update(updateMap)

            Toast.makeText(this, "Successfully Edited!", Toast.LENGTH_SHORT).show()

        }

    }

    private fun setData() {
        val adminId = UUID.randomUUID().toString()
        val ref = db.collection("nethmini").document("98c5b089-4b99-4b57-9ca8-832bd74d4cdd")
        ref.get().addOnSuccessListener {
            if(it != null){
                val etufname = it.data?.get("Name")?.toString()
                val etufaddress = it.data?.get("Address")?.toString()
                val etufnic = it.data?.get("Nic")?.toString()
                val etufPhone = it.data?.get("Num")?.toString()

                uName.text = etufname
                uAddress.text=(etufaddress)
                uNic.text = etufnic
                uMobNum.text = etufPhone
            }
        }
            .addOnFailureListener {
                Toast.makeText(this, "Failed!!" , Toast.LENGTH_SHORT).show()
            }
    }

}