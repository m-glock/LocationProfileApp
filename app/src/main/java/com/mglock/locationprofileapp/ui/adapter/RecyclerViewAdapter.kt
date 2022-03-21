package com.mglock.locationprofileapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.mglock.locationprofileapp.R
import com.mglock.locationprofileapp.database.AppDatabase
import com.mglock.locationprofileapp.database.entities.ActionGroup

class RecyclerViewAdapter(private val dataSet: List<ActionGroup>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
        val switch: SwitchCompat = view.findViewById(R.id.actionSwitch)

        init {
            // Define click listener for the ViewHolder's View.
            switch.setOnCheckedChangeListener { compoundButton, isChecked ->
                //TODO coroutines for DB access
                if(isChecked){
                    // TODO check for permissions
                    val db = AppDatabase.getInstance(view.context)
                    //val actionGroup = db.actionGroupDao().getByTitle(textView.text.toString())
                    db.actionGroupDao().update(isChecked, textView.text.toString())
                } else {
                    // TODO nfo
                    val db = AppDatabase.getInstance(view.context)
                    //val actionGroup = db.actionGroupDao().getByTitle(textView.text.toString())
                    db.actionGroupDao().update(isChecked, textView.text.toString())
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
        holder.textView.text = dataSet[position].title
        holder.switch.isChecked = dataSet[position].active
    }

    override fun getItemCount() = dataSet.size
}