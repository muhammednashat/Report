package com.mnashat_dev.saveme.listofreports

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mnashat_dev.saveme.R
import com.mnashat_dev.saveme.data.ReportDatabase
import com.mnashat_dev.saveme.data.getDatabase
import com.mnashat_dev.saveme.data.models.Report
import com.mnashat_dev.saveme.databinding.FragmentListOfDataBinding
import com.mnashat_dev.saveme.repository.ReportRepository

class ListOfDataFragment : Fragment() {

private lateinit var binding:FragmentListOfDataBinding


    private lateinit var database:ReportDatabase
    private lateinit var reportRepo : ReportRepository
    private lateinit var listOfReportsAdapter : ListOfReportsAdapter
    private lateinit var reports:LiveData<List<Report>>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_list_of_data,container,false)
        listOfReportsAdapter = ListOfReportsAdapter(ReportListener{
            findNavController().navigate(ListOfDataFragmentDirections.actionListOfDataFragmentToDisplayReportFragment(it))
            Log.e("TAG","item  $it " )
        })

        database = getDatabase(requireContext())
        reportRepo = ReportRepository(database)
        reports = reportRepo.reports

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerview()

    }

    private fun setUpRecyclerview(){

        binding.rcListOfReports.adapter = listOfReportsAdapter
        reports.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty() ){
                Log.e("TAG","item23 setUpRecyclerview $it")
                listOfReportsAdapter.submitList(it)
                binding.imageNoData.visibility = View.GONE

            }else{
                Log.e("TAG","item visibility 23")
               binding.constraint.setBackgroundColor(Color.WHITE)
                binding.tvTitle.visibility = View.GONE
                binding.rcListOfReports.visibility = View.GONE
                binding.imageNoData.visibility = View.VISIBLE

            }
        })

    }

}