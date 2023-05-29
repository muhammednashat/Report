package com.mnashat_dev.saveme.data.models


data class ReportForFirebase(
    val id : Long? = null,
    val nameOfDoctor: String? = null,
    val fromWhere: String? = null,
    val conditionsOfDeath: String? = null,
    val caseDetails: String? = null,
    val additionalNotes: String? = null,
    val detailsOfSender: String? = null,
    val timingOfSend: String? = null

)