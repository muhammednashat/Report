package com.mnashat_dev.saveme.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mnashat_dev.saveme.R
import com.mnashat_dev.saveme.data.models.ReportForFirebase
import com.mnashat_dev.saveme.databinding.FragmentSendReportBinding

class SendReportFragment : Fragment() {
    private lateinit var binding: FragmentSendReportBinding
    private var databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Reports")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_send_report, container, false)

        binding.btSendReport.setOnClickListener {
            if (validation()) {
                return@setOnClickListener
            } else {
                addDataToFirebase(createClassToSendToFirebase())
            }
        }

        return binding.root
    }


    // check if edittext empty or not
    private fun validation(): Boolean {

        var fieldsEmpty = false
        if (TextUtils.isEmpty(binding.edNameDoctor.text)) {
            binding.edNameDoctor.error = "من فضلك املىء هذا الحقل"
            fieldsEmpty = true
        }
        if (TextUtils.isEmpty(binding.edFromWhere.text)) {
            binding.edFromWhere.error = "من فضلك املىء هذا الحقل"
            fieldsEmpty = true
        }
        if (TextUtils.isEmpty(binding.edConditionsOfDeath.text)) {
            binding.edConditionsOfDeath.error = "من فضلك املىء هذا الحقل"
            fieldsEmpty = true

        }
        if (TextUtils.isEmpty(binding.edCaseDetails.text)) {
            binding.edCaseDetails.error = "من فضلك املىء هذا الحقل"
            fieldsEmpty = true

        }
        if (TextUtils.isEmpty(binding.edAdditionalNotes.text)) {
            binding.edAdditionalNotes.error = "من فضلك املىء هذا الحقل"
            fieldsEmpty = true

        }
        if (TextUtils.isEmpty(binding.edDetailsOfSender.text)) {
            binding.edDetailsOfSender.error = "من فضلك املىء هذا الحقل"
            fieldsEmpty = true

        }

        return fieldsEmpty
    }


    private fun createClassToSendToFirebase(): ReportForFirebase {

        val nameOfDoctor = binding.edNameDoctor.text.toString()
        val fromWhere = binding.edFromWhere.text.toString()
        val conditionsOfDeath = binding.edConditionsOfDeath.text.toString()
        val caseDetails = binding.edCaseDetails.text.toString()
        val additionalNotes = binding.edAdditionalNotes.text.toString()
        val detailsOfSender = binding.edDetailsOfSender.text.toString()

        return ReportForFirebase(
            nameOfDoctor = nameOfDoctor,
            fromWhere = fromWhere,
            conditionsOfDeath = conditionsOfDeath,
            caseDetails = caseDetails,
            additionalNotes = additionalNotes,
            detailsOfSender = detailsOfSender
        )

    }


    private fun addDataToFirebase(report: ReportForFirebase) {

        val iteemId = databaseReference.push().key!!
        databaseReference.child(iteemId).setValue(report)
            .addOnCompleteListener {
                Toast.makeText(requireContext(), "Don", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Don not", Toast.LENGTH_SHORT).show()
            }

    }


}