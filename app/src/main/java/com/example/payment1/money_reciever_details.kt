package com.example.payment1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class money_reciever_details : AppCompatActivity() {

    private lateinit var Mn_Name: EditText
    private lateinit var Mn_Address: EditText
    private lateinit var Mn_Nic: EditText
    private lateinit var Mn_AccNum: EditText
    private lateinit var M_addDetail: Button

    private lateinit var progressBar: ProgressBar
    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money_reciever_details)


        Mn_Name = findViewById(R.id.etM_name)
        Mn_Address = findViewById(R.id.etM_address)
        Mn_Nic = findViewById(R.id.etM_Nic)
        Mn_AccNum = findViewById(R.id.etM_accNum)
        M_addDetail = findViewById(R.id.M_addDetail)


        M_addDetail.setOnClickListener {
            M_addDetail.visibility = View.VISIBLE

            val adminId_m = UUID.randomUUID().toString()

            val name_m = Mn_Name.text.toString().trim()
            val address_m = Mn_Address.text.toString().trim()
            val nic_m = Mn_Nic.text.toString().trim()
            val Accnum_m = Mn_AccNum.text.toString().trim()

            val userMap = hashMapOf(
                "adminID_m" to adminId_m,
                "Name" to name_m,
                "Address" to address_m,
                "Nic" to nic_m,
                "AccountNum" to Accnum_m
            )


            db.collection( "MoneyReciver").document(adminId_m).set(userMap)
                .addOnSuccessListener {
                    Toast.makeText(this, "Successfully Added!", Toast.LENGTH_SHORT).show()
                    Mn_Name.text.clear()
                    Mn_Address.text.clear()
                    Mn_Nic.text.clear()
                    Mn_AccNum.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this,"Failed!", Toast.LENGTH_SHORT).show()
                }


        }




    }
}