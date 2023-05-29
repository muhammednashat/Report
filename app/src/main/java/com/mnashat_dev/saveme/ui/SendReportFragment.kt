package com.mnashat_dev.saveme.ui

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mnashat_dev.saveme.R
import com.mnashat_dev.saveme.data.ReportDatabase
import com.mnashat_dev.saveme.data.getDatabase
import com.mnashat_dev.saveme.data.models.ReportForFirebase
import com.mnashat_dev.saveme.databinding.FragmentSendReportBinding
import com.mnashat_dev.saveme.repository.ReportRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SendReportFragment : Fragment() {

    private lateinit var binding: FragmentSendReportBinding
    private lateinit var database: ReportDatabase
    private lateinit var reportRepo: ReportRepository
    private var databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Reports")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_send_report, container, false)

        database = getDatabase(requireContext())
        reportRepo = ReportRepository(database)


        binding.btSendReport.setOnClickListener {
            if (validation()) {
                return@setOnClickListener
            } else {
                createClassToSendToFirebase()
                val windowToken = it.windowToken
                hideKeyboard(requireContext(), windowToken)
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
    private fun getCurrentDateTime(): String {
        val dateTimeFormat = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
        return dateTimeFormat.format(Date())
    }


    private fun createClassToSendToFirebase() {

        val nameOfDoctor = binding.edNameDoctor.text.toString()
        val fromWhere = binding.edFromWhere.text.toString()
        val conditionsOfDeath = binding.edConditionsOfDeath.text.toString()
        val caseDetails = binding.edCaseDetails.text.toString()
        val additionalNotes = binding.edAdditionalNotes.text.toString()
        val detailsOfSender = binding.edDetailsOfSender.text.toString()
        var id: Long? = 1L
        lifecycleScope.launch(Dispatchers.Main) {
            if (reportRepo.getLastReport() != null) {
                val report = async { reportRepo.getLastReport() }
                Log.e("TAG", "getLastReport ${report.await()}")
                id = reportRepo.getLastReport()?.id
                Log.e("TAG", "id $id ")

                addDataToFirebase( ReportForFirebase(
                    id = id?.plus(1),
                    nameOfDoctor = nameOfDoctor,
                    fromWhere = fromWhere,
                    conditionsOfDeath = conditionsOfDeath,
                    caseDetails = caseDetails,
                    additionalNotes = additionalNotes,
                    detailsOfSender = detailsOfSender,
                    timingOfSend = getCurrentDateTime()

                )
                )
            }
        }

    }

    private fun addDataToFirebase(report: ReportForFirebase) {

        val iteemId = databaseReference.push().key!!
        databaseReference.child(iteemId).setValue(report)
            .addOnCompleteListener {
                Toast.makeText(requireActivity(), "تم ارسال البيانات بنجاح", Toast.LENGTH_SHORT)
                    .show()
                clearAllFields()
            }
            .addOnFailureListener {
                Toast.makeText(requireActivity(), " لم يتم ارسال البيانات ", Toast.LENGTH_SHORT)
                    .show()
            }

    }
    private fun hideKeyboard(context: Context, windowToken: IBinder) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun clearAllFields() {
        val arrayOdEdit = arrayOf(
            binding.edNameDoctor,
            binding.edFromWhere,
            binding.edConditionsOfDeath,
            binding.edCaseDetails,
            binding.edAdditionalNotes,
            binding.edDetailsOfSender,
        )
        for (edittext in arrayOdEdit) {
            edittext.text.clear()
        }
    }


}