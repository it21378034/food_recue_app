package com.example.payment1


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class button_display_page : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button_display_page)

        val nextbtn = findViewById<Button>(R.id.btnfood)
        nextbtn.setOnClickListener {
            val intent = Intent(this,food_reciver_details::class.java)
            startActivity(intent)
        }

    }
}