package com.example.dofood2.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dofood2.R
import com.example.dofood2.databinding.FragmentFeePendingBinding

class FragmentFeePending : Fragment() {

    private lateinit var binding: FragmentFeePendingBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentFeePendingBinding.inflate(inflater,container,false)
        return binding.root
    }

}