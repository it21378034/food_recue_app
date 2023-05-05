package com.example.dofood2.fragment

//import com.example.dofood2.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.database.DatabaseUtils
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.dofood2.R
import com.example.dofood2.databinding.FragmentAddMemberBinding
import com.example.dofood2.global.DB
import com.example.dofood2.global.Myfunction
import java.text.SimpleDateFormat
import java.util.*


class FragmentAddMember : Fragment() {

    var db: DB? = null
    var oneMonth: String? = ""
    var threeMonths: String? = ""
    var sixMonths: String? = ""
    var oneYear: String? = ""
    var threeYear: String? = ""
    private var actualImagePath = ""
    private lateinit var binding: FragmentAddMemberBinding
    private var gender = "Male"
    private var ID = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddMemberBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = activity?.let { DB(it) }

        ID = arguments!!.getString("ID").toString()

        val cal = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view1, year, monthOfYear, dayOfMonth ->

                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd/MM/yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                binding.edtJoining.setText(sdf.format(cal.time))
            }

        binding.spMembership.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val value = binding.spMembership.selectedItem.toString().trim()
                if (value == "Select") {
                    binding.editExpire.setText("")
                    calculateTotal(binding.spMembership, binding.edtDiscount, binding.edtAmount)
                } else {
                    if (binding.edtJoining.text.toString().trim().isNotEmpty()) {
                        if (value == "1 Month") {
                            calculateExpireDate(1, binding.editExpire)
                            calculateTotal(
                                binding.spMembership,
                                binding.edtDiscount,
                                binding.edtAmount
                            )
                        } else if (value == "3 Month") {
                            calculateExpireDate(3, binding.editExpire)
                            calculateTotal(
                                binding.spMembership,
                                binding.edtDiscount,
                                binding.edtAmount
                            )
                        } else if (value == "6 Month") {
                            calculateExpireDate(6, binding.editExpire)
                            calculateTotal(
                                binding.spMembership,
                                binding.edtDiscount,
                                binding.edtAmount
                            )
                        } else if (value == "1 Year") {
                            calculateExpireDate(12, binding.editExpire)
                            calculateTotal(
                                binding.spMembership,
                                binding.edtDiscount,
                                binding.edtAmount
                            )
                        } else if (value == "3 Years") {
                            calculateExpireDate(36, binding.editExpire)
                            calculateTotal(
                                binding.spMembership,
                                binding.edtDiscount,
                                binding.edtAmount
                            )
                        }
                    } else {
                        showToast("Select Joining Date First")
                        binding.spMembership.setSelection(0)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


        binding.edtDiscount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0 != null) {
                    calculateTotal(binding.spMembership, binding.edtDiscount, binding.edtAmount)
                }
            }
        })

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when (id) {
                R.id.rdMale -> {
                    gender = "Male"
                }
                R.id.rdFeMale -> {
                    gender = "Female"
                }
            }
        }

        binding.btnAddMemberSave.setOnClickListener {
            if (validate()) {
                saveData()
            }
        }

        binding.imgPicDate.setOnClickListener {
            activity?.let { it1 ->
                DatePickerDialog(
                    it1, dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }
        getFee()

        if(ID.trim().isNotEmpty()) {
            loadData()
        }
    }

    private fun getFee() {
        try {
            val sqlQuery = "SELECT * FROM FEE WHERE ID = '1'"
            db?.fireQuery(sqlQuery)?.use {
                if (it.count > 0) {
                    oneMonth = Myfunction.getValue(it, "ONE_MONTH")
                    threeMonths = Myfunction.getValue(it, "THREE_MONTH")
                    sixMonths = Myfunction.getValue(it, "SIX_MONTH")
                    oneMonth = Myfunction.getValue(it, "ONE_MONTH")
                    oneYear = Myfunction.getValue(it, "ONE_YEAR")
                    threeYear = Myfunction.getValue(it, "THREE_YEAR")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun calculateTotal(spMember: Spinner, edtDis: EditText, edtAmt: EditText) {
        val month = spMember.selectedItem.toString().trim()
        var discount = edtDis.text.toString().trim()
        if (edtDis.text.toString().toString().isEmpty()) {
            discount = "0"
        }

        if (month == "Select") {
            edtAmt.setText("")
        } else if (month == "1 Month") {
            if (discount.trim().isEmpty()) {
                discount = "0"
            }

            if (oneMonth!!.trim().isNotEmpty()) {
                val discountAmount = ((oneMonth!!.toDouble() * discount.toDouble()) / 100)
                val total = oneMonth!!.toDouble() - discountAmount
                edtAmt.setText(total.toString())
            }

        } else if (month == "3 Month") {

            if (discount.trim().isEmpty()) {
                discount = "0"
            }

            if (threeMonths!!.trim().isNotEmpty()) {
                val discountAmount = ((threeMonths!!.toDouble() * discount.toDouble()) / 100)
                val total = threeMonths!!.toDouble() - discountAmount
                edtAmt.setText(total.toString())
            }

        } else if (month == "6 Month") {

            if (discount.trim().isEmpty()) {
                discount = "0"
            }

            if (sixMonths!!.trim().isNotEmpty()) {
                val discountAmount = ((sixMonths!!.toDouble() * discount.toDouble()) / 100)
                val total = sixMonths!!.toDouble() - discountAmount
                edtAmt.setText(total.toString())
            }

        } else if (month == "1 Year") {

            if (discount.trim().isEmpty()) {
                discount = "0"
            }

            if (oneYear!!.trim().isNotEmpty()) {
                val discountAmount = ((oneYear!!.toDouble() * discount.toDouble()) / 100)
                val total = oneYear!!.toDouble() - discountAmount
                edtAmt.setText(total.toString())
            }

        } else if (month == "3 Years") {

            if (discount.trim().isEmpty()) {
                discount = "0"
            }

            if (threeYear!!.trim().isNotEmpty()) {
                val discountAmount = ((threeYear!!.toDouble() * discount.toDouble()) / 100)
                val total = threeYear!!.toDouble() - discountAmount
                edtAmt.setText(total.toString())
            }

        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun calculateExpireDate(month: Int, edtExpiry: EditText) {
        val dtStart = binding.edtJoining.text.toString().trim()
        if (dtStart.isNotEmpty()) {
            val format = SimpleDateFormat("dd/MM/yyyy")
            val date1 = format.parse(dtStart)
            val cal = Calendar.getInstance()
            cal.time = date1
            cal.add(Calendar.MONTH, month)

            val myFormat = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            edtExpiry.setText(sdf.format(cal.time))
        }
    }

    private fun showToast(value: String) {
        Toast.makeText(activity, value, Toast.LENGTH_LONG).show()
    }

    private fun validate(): Boolean {
        if (binding.editFirstName.text.toString().trim().isEmpty()) {
            showToast("Enter First Name ")
            return false
        } else if (binding.editLastName.text.toString().trim().isEmpty()) {
            showToast("Enter Last Name ")
            return false
        } else if (binding.editAge.text.toString().trim().isEmpty()) {
            showToast("Enter Age  ")
            return false
        } else if (binding.editMobile.text.toString().trim().isEmpty()) {
            showToast("Enter Mobile Number ")
            return false
        }
        return true
    }

    private fun saveData() =
        try {
            val sqlQuery = "INSERT OR REPLACE INTO MEMBER(ID,FIRST_NAME,LAST_NAME,GENDER,AGE," +
                    "WEIGHT,MOBILE,ADDRESS,DATE_OF_JOINING,MEMBERSHIP,EXPIRE_ON,DISCOUNT,TOTAL,IMAGE_PATH,STATUS)VALUES" +
                    "('" + getIncrementId() + "'," + DatabaseUtils.sqlEscapeString(
                binding.editFirstName.text.toString().trim()
            ) + "," +
                    "" + DatabaseUtils.sqlEscapeString(
                binding.editLastName.text.toString().trim()
            ) + ",'" + gender + "'," +
                    "'" + binding.editAge.text.toString()
                .trim() + "','" + binding.editweight.text.toString().trim() + "'," +
                    "" + binding.editMobile.text.toString()
                .trim() + "," + DatabaseUtils.sqlEscapeString(
                binding.editAddress.text.toString().trim()
            ) + "," +
                    "'" + Myfunction.returnSQLDateFormat(
                binding.edtJoining.text.toString().trim()
            ) + "','" + binding.spMembership.selectedItem.toString().trim() + "'," +
                    "'" + Myfunction.returnSQLDateFormat(
                binding.editExpire.text.toString().trim()
            ) + "','" + binding.edtDiscount.text.toString().trim() + "'," +
                    "'" + binding.edtAmount.text.toString()
                .trim() + "','" + actualImagePath + "','A')"
            db?.executeQuery(sqlQuery)
            showToast("Data saved successfully")

        } catch (e: Exception) {
            e.printStackTrace()
        }

    private fun getIncrementId(): String {
        var incrementId = ""
        try {

            val sqlQuery = "SELECT IFNULL(MAX(ID)+1,'1') AS ID FROM MEMBER"
            db?.fireQuery(sqlQuery)?.use {
                if (it.count > 0) {
                    incrementId = Myfunction.getValue(it, "ID")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return incrementId
    }

    private fun clearData() {
        binding.editFirstName.setText("")
        binding.editLastName.setText("")
        binding.editAge.setText("")
        binding.editweight.setText("")
        binding.editMobile.setText("")
        binding.edtJoining.setText("")
        actualImagePath = ""

        Glide.with(this)
            .load(R.drawable.boy)
            .into(binding.imgPic)
    }

    private fun loadData() {
        try {
            val sqlQuery = "SELECT * FROM MEMBER WHERE ID='$ID'"
            db?.fireQuery(sqlQuery).use {
                if (it != null) {
                    if (it.count > 0) {
                        val firstName = Myfunction.getValue(it, "FIRST_NAME")
                        val lastName = Myfunction.getValue(it, "LAST_NAME")
                        val age = Myfunction.getValue(it, "AGE")
                        val gender = Myfunction.getValue(it, "GENDER")
                        val weight = Myfunction.getValue(it, "WEIGHT")
                        val mobileNo = Myfunction.getValue(it, "MOBILE")
                        val address = Myfunction.getValue(it, "ADDRESS")
                        val dateOfJoin = Myfunction.getValue(it, "DATE_OF_JOINING")
                        val membership = Myfunction.getValue(it, "MEMBERSHIP")
                        val expiry = Myfunction.getValue(it, "EXPIRE_ON")
                        val discount = Myfunction.getValue(it, "DISCOUNT")
                        val total = Myfunction.getValue(it, "TOTAL")
                        actualImagePath = Myfunction.getValue(it, "IMAGE_PATH")

                        binding.editFirstName.setText(firstName)
                        binding.editLastName.setText(lastName)
                        binding.editAge.setText(age)
                        binding.editweight.setText(weight)
                        binding.editMobile.setText(mobileNo)
                        binding.editAddress.setText(address)
                        binding.edtJoining.setText(Myfunction.returnUserDateFormat(dateOfJoin))

                        if (actualImagePath.isNotEmpty()) {
                            Glide.with(this)
                                .load(actualImagePath)
                                .into(binding.imgPic)
                        } else {
                            if (gender == "Male") {
                                Glide.with(this)
                                    .load(R.drawable.boy)
                                    .into(binding.imgPic)
                            } else {
                                Glide.with(this)
                                    .load(R.drawable.girl)
                                    .into(binding.imgPic)
                            }
                        }

                        if (membership.trim().isNotEmpty()) {
                            when (membership) {
                                "1 Month" -> {
                                    binding.spMembership.setSelection(1)
                                }
                                "3 Month" -> {
                                    binding.spMembership.setSelection(2)
                                }
                                "6 Month" -> {
                                    binding.spMembership.setSelection(3)
                                }
                                "1 Year" -> {
                                    binding.spMembership.setSelection(4)
                                }
                                "3 Years" -> {
                                    binding.spMembership.setSelection(5)
                                }
                                else -> {
                                    binding.spMembership.setSelection(0)
                                }
                            }
                        }

                        if (gender == "Male") {
                            binding.radioGroup.check(R.id.rdMale)
                        } else {
                            binding.radioGroup.check(R.id.rdFeMale)
                        }

                        binding.editExpire.setText(Myfunction.returnUserDateFormat(expiry))
                        binding.edtAmount.setText(total)
                        binding.edtDiscount.setText(discount)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}