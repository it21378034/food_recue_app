package com.example.dofood2.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dofood2.R
import com.example.dofood2.databinding.AllMemberListResBinding
import com.example.dofood2.model.AllMember

class AdapterLoadMember(val arrayList:ArrayList<AllMember>): RecyclerView.Adapter<AdapterLoadMember.MyViewHolder>() {

    class MyViewHolder(val binding:AllMemberListResBinding): RecyclerView.ViewHolder(binding.root){

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = AllMemberListResBinding.inflate(LayoutInflater.from(parent.context),parent,false)

            return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AdapterLoadMember.MyViewHolder, position: Int) {
            with(holder){
                with(arrayList[position]){
                    binding.textAdapterName.text = this.firstName+" "+this.lastName
                    binding.txtAdapterAge.text = "Age : "+this.age
                    binding.txtAdapterWeight.text="Weight :"+this.weight
                    binding.txtAdapterMobile.text = "Mobile : "+this.mobile
                    binding.txtAddress.text = this.address
                    binding.txtExpiry.text ="Expiry :"+this.expiryDate

                    if(this.image.isNotEmpty()){
                        Glide.with(holder.itemView.context)
                            .load(this.image)
                            .into(binding.imgPic)
                    }else{
                        if(this.gender =="Male"){
                            Glide.with(holder.itemView.context)
                                .load(R.drawable.boy)
                                .into(binding.imgPic)
                        }else{
                            Glide.with(holder.itemView.context)
                                .load(R.drawable.girl)
                                .into(binding.imgPic)
                            }
                        }
                    }
                }

            }
    }
