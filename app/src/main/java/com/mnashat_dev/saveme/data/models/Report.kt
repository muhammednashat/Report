package com.mnashat_dev.saveme.data.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Entity(tableName = "Reports_table")
@Parcelize
data class Report(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val nameOfDoctor: String? = null,
    val fromWhere: String? = null,
    val conditionsOfDeath: String? = null,
    val caseDetails: String? = null,
    val additionalNotes: String? = null,
    val detailsOfSender: String? = null
) : Parcelable


fun List<ReportForFirebase>.asdomainModel(): List<Report> {
    return map {
        Report(
            id = it.id,
            nameOfDoctor = it.nameOfDoctor,
            fromWhere = it.fromWhere,
            conditionsOfDeath = it.conditionsOfDeath,
            caseDetails = it.caseDetails,
            additionalNotes = it.additionalNotes,
            detailsOfSender = it.detailsOfSender
        )
    }
}