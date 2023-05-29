package com.mnashat_dev.saveme.repository
import android.util.Log
import com.google.firebase.database.*
import com.mnashat_dev.saveme.data.ReportDatabase
import com.mnashat_dev.saveme.data.models.Report
import com.mnashat_dev.saveme.data.models.ReportForFirebase
import com.mnashat_dev.saveme.data.models.asdomainModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ReportRepository(private val database: ReportDatabase) {

    val applicationScope = CoroutineScope(Dispatchers.Default)
    private var databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Reports")
    var listOfReport = arrayListOf<ReportForFirebase>()

    val reports = database.reportDao.getAllReports()


    suspend fun getLastReport(): Report? = withContext(Dispatchers.IO) {
        database.reportDao.getLastReport()
    }

    suspend fun refreshData() {
        withContext(Dispatchers.IO) {
            getDataFromFirebase()
        }
    }

    fun getDataFromFirebaseFirstTime() {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listOfReport.clear()
                if (snapshot.exists()) {
                    for (snapItem in snapshot.children) {
                        val reportForFirebase = snapItem.getValue(ReportForFirebase::class.java)
                        listOfReport.add(reportForFirebase!!)
                    }
                    Log.e("TAG" , " getDataFromFirebaseFirstTime item ${listOfReport.asdomainModel().toTypedArray()} ")
                    applicationScope.launch {
                        database.reportDao.upSert(*listOfReport.asdomainModel().toTypedArray())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun getDataFromFirebase() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listOfReport.clear()
                if (snapshot.exists()) {
                    for (snapItem in snapshot.children) {
                        val reportForFirebase = snapItem.getValue(ReportForFirebase::class.java)
                        listOfReport.add(reportForFirebase!!)
                    }
                    Log.e("TAG" , " item3 ${listOfReport.asdomainModel().toTypedArray()} ")
                    applicationScope.launch {
                        database.reportDao.upSert(*listOfReport.asdomainModel().toTypedArray())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


}