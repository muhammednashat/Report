package com.mnashat_dev.saveme.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mnashat_dev.saveme.R
import com.mnashat_dev.saveme.data.ReportDatabase
import com.mnashat_dev.saveme.data.getDatabase
import com.mnashat_dev.saveme.databinding.FragmentMainBinding
import com.mnashat_dev.saveme.repository.ReportRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private lateinit var database:ReportDatabase
    private lateinit var reportRepo : ReportRepository


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.imageLogin.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToLoginFragment())
        }
        binding.imageSendReport.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToSendReportFragment())
        }

        database = getDatabase(requireContext())
        reportRepo = ReportRepository(database)

        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            refreshData()
        }

        return binding.root
    }


    private suspend fun refreshData(){

        try {
            reportRepo.refreshData()
        } catch (e: Exception) {
            Log.e("TAG", " Exception ${e.message}")
        }
    }
}