package com.example.dofood2.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dofood2.R
import com.example.dofood2.databinding.FragmentAddMemberBinding
import com.example.dofood2.databinding.FragmentAllMemberBinding
import com.example.dofood2.databinding.FragmentAllMemberBinding.*


class FragmentAddMember : Fragment() {

    private lateinit var binding:FragmentAddMemberBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentAddMemberBinding.inflate(inflater, container,false)
        return binding.root
    }

}