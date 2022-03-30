package com.mglock.locationprofileapp.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mglock.locationprofileapp.databinding.ListTileActionsBinding
import com.mglock.locationprofileapp.util.enums.DetailActionTitle

class RecyclerViewActionsAdapter(
    private val dataSet: Array<DetailActionTitle>
) : RecyclerView.Adapter<RecyclerViewActionsAdapter.ViewHolder>() {

    inner class ViewHolder(itemBinding: ListTileActionsBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private val textView: TextView = itemBinding.textView
        var detailActionTitle: DetailActionTitle? = null

        fun setValues(){
            if(detailActionTitle != null){
                textView.text = detailActionTitle!!.title
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemBinding = ListTileActionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.detailActionTitle = dataSet[position]
        holder.setValues()
    }

    override fun getItemCount() = dataSet.size
}