package com.example.dofood2.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dofood2.R
import com.example.dofood2.adapter.AdapterLoadMember
import com.example.dofood2.databinding.FragmentAllMemberBinding
import com.example.dofood2.global.DB
import com.example.dofood2.global.Myfunction
import com.example.dofood2.model.AllMember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FragmentAllMember : BaseFragment() {
    private val TAG = "FragmentAllMember"
    var db: DB? = null
    var adapter: AdapterLoadMember? = null
    var arrayList: ArrayList<AllMember> = ArrayList()
    private lateinit var binding: FragmentAllMemberBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAllMemberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = activity?.let { DB(it) }

        binding.rdGroupMember.setOnCheckedChangeListener { radioGroup, i ->
            when (id) {
                R.id.rdActiveMember -> {

                }
                R.id.rdInActiveMember -> {

                }
            }
        }
    }

    fun OnResume() {
        super.onResume()
        loadData("A")
    }

    fun <R> CoroutineScope.executeAsyncTask(
        onPreExecute: () -> Unit,
        doInBackground: () -> R,
        onPostExecute: (R) -> Unit
    ) = launch {
        onPreExecute()
        val result = withContext(Dispatchers.IO) {
            doInBackground()
        }
        onPostExecute(result)
    }

    private fun loadData(memberStatus: String) {
        lifecycleScope.executeAsyncTask(onPreExecute = {
            showDialog("Processing....")
        }, doInBackground = {

            val sqlQuery = "SELECT * FROM MEMBER WHERE STATUS='$memberStatus'"
            db?.fireQuery(sqlQuery)?.use {
                if (it.count > 0) {
                    it.moveToFirst()
                    do {
                        val list = AllMember(
                            id = Myfunction.getValue(it, "ID"),
                            firstName = Myfunction.getValue(it, "FIRST_NAME"),
                            lastName = Myfunction.getValue(it, "LAST_NAME"),
                            age = Myfunction.getValue(it, "AGE"),
                            gender = Myfunction.getValue(it, "GENDER"),
                            weight = Myfunction.getValue(it, "WEIGHT"),
                            mobile = Myfunction.getValue(it, "MOBILE"),
                            address = Myfunction.getValue(it, "ADDRESS"),
                            image = Myfunction.getValue(it, "IMAGE_PATH"),
                            dateOfJoining = Myfunction.returnSQLDateFormat(
                                Myfunction.getValue(
                                    it,
                                    "DATE_OF_JOINING"
                                )
                            ),
                            expiryDate = Myfunction.returnSQLDateFormat(
                                Myfunction.getValue(
                                    it,
                                    "EXPIRE_ON"
                                )
                            )
                        )

                        arrayList.add(list)
                    } while (it.moveToNext())

                }
            }


        }, onPostExecute = {
            if (arrayList.size > 0) {
                binding.recyclerViewMember.visibility = View.VISIBLE
                binding.txtAllMemberNDF.visibility = View.GONE

                adapter = AdapterLoadMember(arrayList)
                binding.recyclerViewMember.layoutManager = LinearLayoutManager(activity)
                binding.recyclerViewMember.adapter = adapter
            } else {
                binding.recyclerViewMember.visibility = View.GONE
                binding.txtAllMemberNDF.visibility = View.VISIBLE
            }
            closeDialog()
        })
    }


}






