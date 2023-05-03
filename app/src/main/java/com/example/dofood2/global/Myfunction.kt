package com.example.dofood2.global

import android.annotation.SuppressLint
import android.database.Cursor
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class Myfunction {

    companion object{
        fun getValue(cursor: Cursor, columnName:String):String{
            var value:String = ""

            try{
                val col = cursor.getColumnIndex(columnName)
                value = cursor.getString(col)

            }catch (e:Exception){
                e.printStackTrace()
                Log.d("MyFunction","getValue ${e.printStackTrace()}")
                value = ""
            }
            return value
        }

        @SuppressLint("SimpleDateFormat")
        fun returnUserSQLDateFormat(date:String): String{
            try {
                if(date.trim().isNotEmpty()){
                    val dateFormat1 = SimpleDateFormat("yyyy-MM-dd")
                    val firstDate = dateFormat1.parse(date)
                    val dateFormat2 = SimpleDateFormat("dd/MM/yyyy")
                    return dateFormat2.format(firstDate as Date)
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
            return ""
        }
    }
}