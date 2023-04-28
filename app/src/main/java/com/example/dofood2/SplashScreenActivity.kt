package com.example.dofood2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import com.example.dofood2.activity.HomeActivity
import com.example.dofood2.activity.LoginActivity
import com.example.dofood2.databinding.ActivityLoginBinding
import com.example.dofood2.databinding.ActivityMainBinding
import com.example.dofood2.global.DB
import com.example.dofood2.manager.SessionManager

class SplashScreenActivity : AppCompatActivity() {
    private var nDelayHandler: Handler? = null
    private val splash_delay: Long = 3000 //3 seconds
    var db: DB? = null
    var session: SessionManager? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DB(this)
        session = SessionManager(this)

        insertAdminData()
        nDelayHandler = Handler()
        nDelayHandler?.postDelayed(nRunnable, splash_delay)
    }

    private val nRunnable: Runnable = Runnable {

        if(session?.isLoggedIn==true){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }else {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun insertAdminData() {
        try {
            val sqlCheck = "SELECT * FROM ADMIN"
            db?.fireQuery(sqlCheck)?.use {
                if (it.count > 0) {
                    Log.d("SplashActivity", "data available")
                } else {
                    val sqlQuery =
                        "INSERT OR REPLACE INTO ADMIN(ID,USER_NAME,PASSWORD,MOBILE)VALUES('1','Admin','12345','8888888')"
                    db?.executeQuery(sqlQuery)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        nDelayHandler?.removeCallbacks(nRunnable)
    }
}