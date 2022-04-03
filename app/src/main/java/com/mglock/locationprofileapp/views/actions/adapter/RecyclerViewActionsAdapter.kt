package com.mglock.locationprofileapp.views.actions.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mglock.locationprofileapp.databinding.ListTileActionsBinding
import com.mglock.locationprofileapp.util.enums.DetailActionOption

class RecyclerViewActionsAdapter(
    private val dataSet: Array<DetailActionOption>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerViewActionsAdapter.ViewHolder>() {

    inner class ViewHolder(itemBinding: ListTileActionsBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private val textView: TextView = itemBinding.textView
        var detailActionOption: DetailActionOption? = null
        var infoButton: ImageButton = itemBinding.infoButton

        fun setValues(){
            if(detailActionOption != null){
                textView.text = detailActionOption!!.title
                infoButton.setOnClickListener {
                    AlertDialog.Builder(context)
                        .setTitle("Action Information")
                        .setMessage(detailActionOption!!.getInfoText())
                        .setPositiveButton("Okay", null)
                        .show()
                }
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
        holder.detailActionOption = dataSet[position]

        holder.setValues()
    }

    override fun getItemCount() = dataSet.size
}