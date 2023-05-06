package com.example.payment1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.UUID

class Data : AppCompatActivity() {

    private lateinit var DName: TextView
    private lateinit var DID: TextView
    private lateinit var DContactNumber: TextView
    private lateinit var DEmail: TextView

    private var db = Firebase.firestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        DName = findViewById(R.id.txtname)
        DID = findViewById(R.id.txtid)
        DContactNumber = findViewById(R.id.txtcontact)
        DEmail = findViewById(R.id.txtemail)

        val dataID = UUID.randomUUID().toString()
        val ref = db.collection("detailsadd").document("30a732f2-1e90-428e-bd18-1bc32f665ed5")
        ref.get().addOnSuccessListener{
            if (it!=null){
                val etRecName = it.data?.get("name")?.toString()
                val etRecId = it.data?.get("idc")?.toString()
                val etRecContact = it.data?.get("contact")?.toString()
                val etRecEmail = it.data?.get("email")?.toString()

                DName.text = etRecName
                DID.text = etRecId
                DContactNumber.text = etRecContact
                DEmail.text = etRecEmail
            }
        }
            .addOnFailureListener{
                Toast.makeText(this,"Failed!",Toast.LENGTH_SHORT).show()

            }

    }
}