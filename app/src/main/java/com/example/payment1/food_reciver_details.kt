package com.example.payment1
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class food_reciver_details : AppCompatActivity() {

    private lateinit var edfname: EditText
    private lateinit var edfaddress: EditText
    private lateinit var edfnic: EditText
    private lateinit var edfmnum: EditText
    private lateinit var btnadd: Button

    private lateinit var progressBar: ProgressBar
    private var db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_reciver_details)


        edfname = findViewById(R.id.etufname)
        edfaddress = findViewById(R.id.etufaddress)
        edfnic = findViewById(R.id.etufnic)
        edfmnum = findViewById(R.id.edufmnum)
        btnadd = findViewById(R.id.addDetails_btn)


        btnadd.setOnClickListener {
            btnadd.visibility = View.VISIBLE

            val adminId = UUID.randomUUID().toString()

            val name = edfname.text.toString().trim()
            val address = edfaddress.text.toString().trim()
            val nic = edfnic.text.toString().trim()
            val num = edfmnum.text.toString().trim()

            val userMap = hashMapOf(
                "adminID" to adminId,
                "Name" to name,
                "Address" to address,
                "Nic" to nic,
                "Num" to num
            )


            db.collection( "nethmini").document(adminId).set(userMap)
                .addOnSuccessListener {
                    Toast.makeText(this, "Successfully Added!", Toast.LENGTH_SHORT).show()
                    edfname.text.clear()
                    edfaddress.text.clear()
                    edfnic.text.clear()
                    edfmnum.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this,"Failed!",Toast.LENGTH_SHORT).show()
                }


        }




    }
}


