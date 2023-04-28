package com.example.dofood2.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.dofood2.R
import com.example.dofood2.databinding.FragmentAddUpdateFeeBinding
import com.example.dofood2.global.DB
import com.example.dofood2.global.Myfunction


class FragmentAddUpdateFee : Fragment() {

    private lateinit var binding: FragmentAddUpdateFeeBinding
    var db:DB?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentAddUpdateFeeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = activity?.let { DB(it) }

        binding.btnAddMemberShip.setOnClickListener {
            if (validate()){
                saveData()
            }
        }
        fillData()
    }

    private fun validate():Boolean{

        if (binding.edtOneMonth.text.toString().trim().isEmpty()){
            showToast("Enter One Month Fee")
            return false
        }else if(binding.edtThreeMonth.text.toString().trim().isEmpty()){
            showToast("Enter Three Months Fee")
            return false
        }else if(binding.edtSixMonth.text.toString().trim().isEmpty()) {
            showToast("Enter Six Months Fee")
            return false
        }else if(binding.edtOneYear.text.toString().trim().isEmpty()) {
            showToast("Enter One Year Fee")
            return false
        }else if(binding.edtThreeYear.text.toString().trim().isEmpty()) {
            showToast("Enter Three Years Fee")
            return false
        }else if(binding.edtThreeYear.text.toString().trim().isEmpty()) {
            showToast("Enter Three Years Fee")
            return false
        }
        return true
    }

    private fun saveData(){
        try {
            val sqlQuery = "INSERT OR REPLACE INTO FEE(ID,ONE_MONTH,THREE_MONTH,SIX_MONTH,ONE_YEAR,THREE_YEAR)VALUES"+
                    "('1','"+binding.edtOneMonth.text.toString().trim()+"','"+binding.edtThreeMonth.text.toString().trim()+"'" +
                    "'"+binding.edtSixMonth.text.toString().trim()+"','"+binding.edtOneYear.text.toString().trim()+"'," +
                    "'"+binding.edtThreeYear.text.toString().trim()+"')"

            db?.executeQuery(sqlQuery)
            showToast("Membership data saved successfully")

        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun fillData(){
        try {
            val sqlQuery = "SELECT * FROM FEE WHERE ID = '1'"
            db?.fireQuery(sqlQuery).use {
                if (it != null) {
                    if(it.count>0){
                        it.moveToFirst()
                        binding.edtOneMonth.setText(it?.let { it1 -> Myfunction.getValue(it1,"ONE_MONTH") })
                        binding.edtThreeMonth.setText(it?.let { it1 -> Myfunction.getValue(it1,"THREE_MONTH") })
                        binding.edtSixMonth.setText(it?.let { it1 -> Myfunction.getValue(it1,"SIX_MONTH") })
                        binding.edtOneYear.setText(it?.let { it1 -> Myfunction.getValue(it1,"ONE_YEAR") })
                        binding.edtThreeYear.setText(it?.let { it1 -> Myfunction.getValue(it1,"THREE_YEAR") })
                    }
                }
          }

        }catch (e:Exception){
            e.printStackTrace()
        }

    }


    private fun showToast(value: String){
        Toast.makeText(activity, value,Toast.LENGTH_LONG).show()
    }

}