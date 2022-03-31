package com.mglock.locationprofileapp.views.profiles.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mglock.locationprofileapp.database.entities.DetailAction
import com.mglock.locationprofileapp.databinding.ListTileProfileActionsBinding
import com.mglock.locationprofileapp.viewmodels.profiles.AddActionsToProfileViewModel

class RecyclerViewProfileActionsAdapter(
    private val dataSet: List<DetailAction>,
    private val viewModel: AddActionsToProfileViewModel
) : RecyclerView.Adapter<RecyclerViewProfileActionsAdapter.ViewHolder>() {

    inner class ViewHolder(itemBinding: ListTileProfileActionsBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private val textView: TextView = itemBinding.actionText
        var detailAction: DetailAction? = null

        fun setValues(){
            if(detailAction != null){
                textView.text = detailAction!!.toString()
            }
        }

        init {
            itemBinding.buttonDelete.setOnClickListener {
                viewModel.deleteAction(detailAction!!)
            }
            itemBinding.buttonEdit.setOnClickListener {
                //TODO
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemBinding = ListTileProfileActionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.detailAction = dataSet[position]
        holder.setValues()
    }

    override fun getItemCount() = dataSet.size
}