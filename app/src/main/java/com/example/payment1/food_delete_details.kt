package com.example.payment1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class food_delete_details : AppCompatActivity() {

    private lateinit var dName: TextView
    private lateinit var dAddress: TextView
    private lateinit var dNic: TextView
    private lateinit var dMobNum: TextView
    private lateinit var delete: Button


    private var db = FirebaseFirestore.getInstance()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_delete_details)

        dName = findViewById(R.id.etufname)
        dAddress = findViewById(R.id.etufaddress)
        dNic = findViewById(R.id.etufnic)
        dMobNum = findViewById(R.id.etufPhone)
        delete = findViewById(R.id.btnupdate)


        setData()

        delete.setOnClickListener {

            val deleteMap = mapOf(
                "Name" to FieldValue.delete(),
                "Address" to FieldValue.delete(),
                "Nic" to FieldValue.delete(),
                "Num" to FieldValue.delete()
            )

            val adminId = UUID.randomUUID().toString()
            db.collection("nethmini").document(adminId).update(deleteMap)
                .addOnSuccessListener {
                    Toast.makeText(this, "Successfully deleted!!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed!!" , Toast.LENGTH_SHORT).show()
                }


        }

    }

    private fun setData() {
        val adminId = UUID.randomUUID().toString()
        val ref = db.collection("nethmini").document("2e62c2b8-64af-41d7-9573-46add0d364ca")
        ref.get().addOnSuccessListener {
            if(it != null){
                val name = it.data?.get("Name")?.toString()
                val address = it.data?.get("Address")?.toString()
                val nic = it.data?.get("Nic")?.toString()
                val num = it.data?.get("Num")?.toString()

                dName.setText(name)
                dAddress.setText(address)
                dNic.setText(nic)
                dMobNum.setText(num)
            }
        }
            .addOnFailureListener {
                Toast.makeText(this, "Failed!!" , Toast.LENGTH_SHORT).show()
            }
    }
}