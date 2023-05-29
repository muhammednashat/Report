package com.mnashat_dev.saveme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.mnashat_dev.saveme.databinding.FragmentDisplayReportBinding

class DisplayReportFragment : Fragment() {
private lateinit var binding:FragmentDisplayReportBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_display_report,container,false)

        val args =    DisplayReportFragmentArgs.fromBundle(requireArguments())
        binding.report = args.report

return binding.root
    }

}