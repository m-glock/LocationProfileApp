package com.mglock.locationprofileapp.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.mglock.locationprofileapp.database.entities.ActionGroup
import com.mglock.locationprofileapp.databinding.ListTileActionsBinding

class RecyclerViewActionsAdapter(private val dataSet: List<ActionGroup>) :
    RecyclerView.Adapter<RecyclerViewActionsAdapter.ViewHolder>() {

    class ViewHolder(itemBinding: ListTileActionsBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        val textView: TextView = itemBinding.textView
        val switch: SwitchCompat = itemBinding.actionSwitch
        var actionGroup: ActionGroup? = null

        fun setValues(){
            if(actionGroup != null){
                textView.text = actionGroup!!.title
                switch.isChecked = actionGroup!!.active
            }
        }

        init {
            // Define click listener for the ViewHolder's View.
            switch.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    // TODO check for permissions
                    actionGroup?.active = isChecked
                } else {
                    actionGroup?.active = isChecked
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
        holder.actionGroup = dataSet[position]
        holder.setValues()
    }

    override fun getItemCount() = dataSet.size
}