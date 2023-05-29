package com.mnashat_dev.saveme.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mnashat_dev.saveme.data.models.Report

@BindingAdapter("senderName")
fun TextView.setSenderName(report: Report) {
    text = "المرسل /  ${report.detailsOfSender}"

}
@BindingAdapter("nameOfDoctor")
fun TextView.setNameOfDoctor(report: Report){
    text = report.nameOfDoctor
}
@BindingAdapter("timingOfSend")
fun TextView.setTimingOfSend(report: Report){
    text = report.timingOfSend
}
@BindingAdapter("additionalNotes")
fun TextView.setAdditionalNotes(report: Report){
    text = report.additionalNotes
}
@BindingAdapter("caseDetails")
fun TextView.setCaseDetails(report: Report){
    text = report.caseDetails
}
@BindingAdapter("fromWhere")
fun TextView.setFromWhere(report: Report){
    text = report.fromWhere
}

@BindingAdapter("conditionsOfDeath")
fun TextView.setConditionsOfDeath(report: Report){
    text = report.conditionsOfDeath
}