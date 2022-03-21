package com.mglock.locationprofileapp.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.mglock.locationprofileapp.R
import com.mglock.locationprofileapp.database.entities.ActionGroup

class RecyclerViewAdapter(private val dataSet: List<ActionGroup>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
        val switch: SwitchCompat = view.findViewById(R.id.actionSwitch)
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
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_tile_actions, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.actionGroup = dataSet[position]
        holder.setValues()
    }

    override fun getItemCount() = dataSet.size
}