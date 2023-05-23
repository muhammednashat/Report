package com.mnashat_dev.saveme.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.mnashat_dev.saveme.R
import com.mnashat_dev.saveme.databinding.FragmentListOfDataBinding

class ListOfDataFragment : Fragment() {

private lateinit var binding:FragmentListOfDataBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_list_of_data,container,false)

        return binding.root
    }

}