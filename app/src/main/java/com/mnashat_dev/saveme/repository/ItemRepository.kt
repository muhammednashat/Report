package com.mnashat_dev.saveme.repository
import com.google.firebase.database.*
import com.mnashat_dev.saveme.data.ReportDatabase
import com.mnashat_dev.saveme.data.models.ReportForFirebase
import com.mnashat_dev.saveme.data.models.asdomainModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ItemRepository(private val database: ReportDatabase) {

    val applicationScope = CoroutineScope(Dispatchers.Default)
    private var databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Reports")
    var listOfReport = arrayListOf<ReportForFirebase>()

    val reports = database.reportDao.getAllReports()

    suspend fun refreshData() {
        withContext(Dispatchers.IO) {
            getDataFromFirebase()
        }
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