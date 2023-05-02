package com.example.dofood2.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import  com.example.dofood2.Manifest
import com.example.dofood2.R
import com.example.dofood2.databinding.FragmentAddMemberBinding
import com.example.dofood2.databinding.FragmentAllMemberBinding
import com.example.dofood2.databinding.FragmentAllMemberBinding.*
import com.example.dofood2.global.DB
import com.example.dofood2.global.Myfunction
import com.github.florent37.runtimepermission.RuntimePermission
import java.text.SimpleDateFormat
import java.util.*




class FragmentAddMember : Fragment() {

    var db:DB?=null
    var oneMonth:String? =""
    var threeMonths:String? =""
    var sixMonths:String? =""
    var oneYear:String? =""
    var threeYear:String? =""
    private lateinit var binding:FragmentAddMemberBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddMemberBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = activity?.let { DB(it) }

        val cal = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { view1, year, monthOfYear, dayOfMonth ->

            cal.set(Calendar.YEAR,year)
            cal.set(Calendar.MONTH,monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH,dayOfMonth)

            val  myFormat = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            binding.edtJoining.setText(sdf.format(cal.time))
        }

        binding.spMembership.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val value = binding.spMembership.selectedItem.toString().trim()
                if(value == "Select"){
                    binding.editExpire.setText("")
                    calculateTotal(binding.spMembership, binding.edtDiscount, binding.edtAmount)
                }else{
                    if(binding.edtJoining.text.toString().trim().isNotEmpty()){
                        if(value == "1 Month"){
                            calculateExpireDate(1,binding.editExpire)
                            calculateTotal(binding.spMembership, binding.edtDiscount, binding.edtAmount)
                        }else if(value == "3 Month"){
                            calculateExpireDate(3,binding.editExpire)
                            calculateTotal(binding.spMembership, binding.edtDiscount, binding.edtAmount)
                        }else if(value == "6 Month"){
                            calculateExpireDate(6,binding.editExpire)
                            calculateTotal(binding.spMembership, binding.edtDiscount, binding.edtAmount)
                        }else if(value == "1 Year"){
                            calculateExpireDate(12,binding.editExpire)
                            calculateTotal(binding.spMembership, binding.edtDiscount, binding.edtAmount)
                        }else if(value == "3 Years"){
                            calculateExpireDate(36,binding.editExpire)
                            calculateTotal(binding.spMembership, binding.edtDiscount, binding.edtAmount)
                        }
                    } else{
                        showToast("Select Joining Date First")
                        binding.spMembership.setSelection(0)
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


        binding.edtDiscount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0!=null){
                    calculateTotal(binding.spMembership, binding.edtDiscount, binding.edtAmount)
                }
            }
        })

        binding.imgPicDate.setOnClickListener {
            activity?.let { it1 -> DatePickerDialog(it1,dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)).show() }
        }

        binding.imgTakeImage.setOnClickListener {
            getImage()
        }

        getFee()
    }

    private  fun getFee(){
        try {
            val sqlQuery = "SELECT * FROM FEE WHERE ID = '1'"
            db?.fireQuery(sqlQuery)?.use {
                oneMonth = Myfunction.getValue(it,"ONE_MONTH")
                threeMonths = Myfunction.getValue(it,"THREE_MONTH")
                sixMonths = Myfunction.getValue(it,"SIX_MONTH")
                oneMonth = Myfunction.getValue(it,"ONE_MONTH")
                oneYear = Myfunction.getValue(it,"ONE_YEAR")
                threeYear = Myfunction.getValue(it,"THREE_YEAR")
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private  fun calculateTotal(spMember:Spinner,edtDis:EditText,edtAmt:EditText){
        val month = spMember.selectedItem.toString().trim()
        var discount =  edtDis.text.toString().trim()
        if(edtDis.text.toString().toString().isEmpty()){
            discount = "0"
        }

        if(month =="Select"){
            edtAmt .setText("")
        }else if (month == "1 Month"){
            if(discount.trim().isEmpty()){
                discount = "0"
            }

            if(oneMonth!!.trim().isNotEmpty()){
                val discountAmount = ((oneMonth!!.toDouble() * discount.toDouble())/100)
                val total = oneMonth!!.toDouble() - discountAmount
                edtAmt.setText(total.toString())
            }

        }else if (month == "3 Month"){

            if(discount.trim().isEmpty()){
                discount = "0"
            }

            if(threeMonths!!.trim().isNotEmpty()){
                val discountAmount = ((threeMonths!!.toDouble() * discount.toDouble())/100)
                val total = threeMonths!!.toDouble() - discountAmount
                edtAmt.setText(total.toString())
            }

        }else if (month == "6 Month"){

            if(discount.trim().isEmpty()){
                discount = "0"
            }

            if(sixMonths!!.trim().isNotEmpty()){
                val discountAmount = ((sixMonths!!.toDouble() * discount.toDouble())/100)
                val total = sixMonths!!.toDouble() - discountAmount
                edtAmt.setText(total.toString())
            }

        }else if (month == "1 Year"){

            if(discount.trim().isEmpty()){
                discount = "0"
            }

            if(oneYear!!.trim().isNotEmpty()){
                val discountAmount = ((oneYear!!.toDouble() * discount.toDouble())/100)
                val total = oneYear!!.toDouble() - discountAmount
                edtAmt.setText(total.toString())
            }

        }else if (month == "3 Years"){

            if(discount.trim().isEmpty()){
                discount = "0"
            }

            if(threeYear!!.trim().isNotEmpty()){
                val discountAmount = ((threeYear!!.toDouble() * discount.toDouble())/100)
                val total = threeYear!!.toDouble() - discountAmount
                edtAmt.setText(total.toString())
            }

        }
    }


    private fun calculateExpireDate(month:Int, edtExpiry:EditText){
        val dtStart = binding.edtJoining.text.toString().trim()
        if(dtStart.isNotEmpty()){
            val format = SimpleDateFormat("dd/MM/yyyy")
            val date1 = format.parse(dtStart)
            val cal = Calendar.getInstance()
            cal.time = date1
            cal.add(Calendar.MONTH,month)

            val myFormat ="dd/MM/yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            edtExpiry.setText(sdf.format(cal.time))
        }

    }


    private fun showToast(value: String){
        Toast.makeText(activity, value, Toast.LENGTH_LONG).show()
    }

    private  fun getImage(){
        val items:Array<CharSequence>
        try {

            items = arrayOf("Take Photo","Choose Image", "Cancel")
            val builder = android.app.AlertDialog.Builder(activity)
            builder.setCancelable(false)
            builder.setTitle("Select Image")
            builder.setItems(items) { dialogInterface, i ->

                if(items[i]=="Take Photo"){
                   RuntimePermission.askPermission(this)
                       .request(android.Manifest.permission.CAMERA)
                       .onAccepted{

                       }
                       .onDenied{
                           android.app.AlertDialog.Builder(activity)
                               .setMessage("Please accept our permission to capture image")
                               .setPositiveButton("Yes") { dialogInterface, i ->
                                    it.askAgain()
                               }
                               .setNegativeButton("No"){ dialogInterface, i ->
                                     dialogInterface.dismiss()
                               }
                               .show()
                       }
                       .ask()


                }else if (items[i]=="Choose Image"){
                    RuntimePermission.askPermission(this)
                        .request(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .onAccepted{

                        }
                        .onDenied{
                            android.app.AlertDialog.Builder(activity)
                                .setMessage("Please accept our permission to capture image")
                                .setPositiveButton("Yes") { dialogInterface, i ->
                                    it.askAgain()
                                }
                                .setNegativeButton("No"){ dialogInterface, i ->
                                    dialogInterface.dismiss()
                                }
                                .show()
                        }
                        .ask()
                }else{
                    dialogInterface.dismiss()
                }
            }
            builder.show()

        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}