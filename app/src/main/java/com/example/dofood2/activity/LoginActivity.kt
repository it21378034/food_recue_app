package com.example.dofood2.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dofood2.R
import com.example.dofood2.databinding.ActivityLoginBinding
import com.example.dofood2.databinding.ForgetPasswordDialogBinding
import com.example.dofood2.global.DB
import com.example.dofood2.global.Myfunction
import com.example.dofood2.manager.SessionManager

class LoginActivity : AppCompatActivity() {
    var db: DB? = null
    var session: SessionManager? = null
    var editUserName: EditText? = null
    var editPassword: EditText? = null
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DB(this)
        session = SessionManager(this)
        editUserName = binding.editUserName
        editPassword = binding.editPassword

        binding.btnLogin.setOnClickListener {
            if (validateLogin()) {
                getLogin()
            }

        }
        binding.txtForgotPassword.setOnClickListener {
            showDialog()
        }
    }

    private fun getLogin() {
        try {
            val sqlQuery = "SELECT * FROM ADMIN WHERE USER_NAME='" + editUserName?.text.toString()
                .trim() + "'" +
                    "AND PASSWORD = '" + editPassword?.text.toString().trim() + "'AND ID='1'"
            db?.fireQuery(sqlQuery)?.use {
                if (it.count > 0) {
                    session?.setLogin(true)
                    Toast.makeText(this, "Successfully Log In", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    session?.setLogin(false)
                    Toast.makeText(this, "Log In Failed", Toast.LENGTH_LONG).show()
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun validateLogin(): Boolean {
        if (editUserName?.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter User Name", Toast.LENGTH_LONG).show()
            return false
        } else if (editPassword?.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun showDialog(){
        val binding2 = ForgetPasswordDialogBinding.inflate(LayoutInflater.from(this))
        val dialog = Dialog(this, R.style.AlertDialogCustom)
        dialog.setContentView(binding2.root)
        dialog.setCancelable(false)
        dialog.show()

        binding2.btnForgetSubmit.setOnClickListener {
            if(binding2.edtForgetMobile.text.toString().trim().isNotEmpty()){
                checkData(binding2.edtForgetMobile.text.toString().trim(), binding2.txtYourPassword)
            }else{
                Toast.makeText(this,"Mobile No.", Toast.LENGTH_LONG).show()
            }
        }
        binding2.imgBackButton.setOnClickListener {
            dialog.dismiss()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkData(mobile:String, txtShowPassword:TextView){
        try {
            val sqlQuery = "SELECT * FROM ADMIN WHERE MOBILE='$mobile'"
            db?.fireQuery(sqlQuery)?.use {
                if(it.count>0){
                    val password = Myfunction.getValue(it,"PASSWORD")
                    txtShowPassword.visibility = View.VISIBLE
                    txtShowPassword.text="Your Password is : $password"
                }else{
                    Toast.makeText(this,"Incorrect Mobile Number", Toast.LENGTH_LONG).show()
                    txtShowPassword.visibility = View.GONE
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

}