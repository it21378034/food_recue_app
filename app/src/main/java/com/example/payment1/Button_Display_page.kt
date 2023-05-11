package com.example.payment1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class Button_Display_page : AppCompatActivity() {

    private lateinit var moneybtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button_display_page)

        moneybtn = findViewById(R.id.btnmoney)

        moneybtn.setOnClickListener {
            val Intent = Intent(this,once_payment_page::class.java)
            startActivity(Intent)
        }
    }

}