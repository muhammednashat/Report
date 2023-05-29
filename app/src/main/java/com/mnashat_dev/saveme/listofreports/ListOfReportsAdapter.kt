package com.mnashat_dev.saveme.listofreports


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mnashat_dev.saveme.data.models.Report
import com.mnashat_dev.saveme.databinding.ItemViewListOfDataBinding


class ListOfReportsAdapter(private val clickListener: ReportListener) :
    ListAdapter<Report, ListOfReportsAdapter.ListOfReportsViewHolder>(ItemDiffUtil()) {


    class ListOfReportsViewHolder private constructor(val binding: ItemViewListOfDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            report: Report, clickListener: ReportListener
        ) {

            binding.report = report
            binding.clickListener = clickListener
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): ListOfReportsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemViewListOfDataBinding.inflate(layoutInflater, parent, false)

                return ListOfReportsViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListOfReportsViewHolder {
        return ListOfReportsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ListOfReportsViewHolder, position: Int) {
//        if (position == 0 ){
//            holder.binding.scrollView.visibility  = View.VISIBLE
//        }
        holder.bind(getItem(position), clickListener)
    }
}


class ItemDiffUtil() : DiffUtil.ItemCallback<Report>() {
    override fun areItemsTheSame(oldHadith: Report, newHadith: Report): Boolean {
        return oldHadith.id == newHadith.id
    }

    override fun areContentsTheSame(oldHadith: Report, newHadith: Report): Boolean {
        return oldHadith == newHadith
    }

}

class ReportListener(private val clickListener: (report: Report) -> Unit) {

    fun onClick(report: Report) = clickListener(report)
}