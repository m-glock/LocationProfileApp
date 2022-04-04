package com.mglock.locationprofileapp.views.profiles.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mglock.locationprofileapp.database.entities.DetailAction
import com.mglock.locationprofileapp.databinding.ListTileWithSubtitleBinding
import com.mglock.locationprofileapp.viewmodels.profiles.AddActionsToProfileViewModel

class RecyclerViewProfileActionsAdapter(
    private val dataSet: List<DetailAction>,
    private val viewModel: AddActionsToProfileViewModel,
) : RecyclerView.Adapter<RecyclerViewProfileActionsAdapter.ViewHolder>() {

    inner class ViewHolder(itemBinding: ListTileWithSubtitleBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private val textViewTitle: TextView = itemBinding.listTileTitle
        private val textViewSubtitle: TextView = itemBinding.listTileSubtitle
        var detailAction: DetailAction? = null

        fun setValues(){
            if(detailAction != null){
                textViewTitle.text = detailAction!!.title.title
                textViewSubtitle.text = detailAction!!.valueToString()
            }
        }

        init {
            itemBinding.buttonDelete.setOnClickListener {
                viewModel.deleteAction(detailAction!!)
            }
            // do not show edit button, actions can only be created or deleted
            itemBinding.buttonEdit.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemBinding = ListTileWithSubtitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.detailAction = dataSet[position]
        holder.setValues()
    }

    override fun getItemCount() = dataSet.size
}