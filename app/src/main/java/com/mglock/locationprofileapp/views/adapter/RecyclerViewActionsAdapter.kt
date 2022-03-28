package com.mglock.locationprofileapp.views.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.karumi.dexter.Dexter
import com.mglock.locationprofileapp.database.entities.ActionGroup
import com.mglock.locationprofileapp.databinding.ListTileActionsBinding
import com.mglock.locationprofileapp.util.PermissionListener
import com.mglock.locationprofileapp.util.enums.ActionGroupTitle
import com.mglock.locationprofileapp.viewmodels.ActionsViewModel

class RecyclerViewActionsAdapter(
    private val dataSet: List<ActionGroup>,
    private val viewModel: ActionsViewModel,
    private val context: Context
) : RecyclerView.Adapter<RecyclerViewActionsAdapter.ViewHolder>() {

    inner class ViewHolder(itemBinding: ListTileActionsBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private val textView: TextView = itemBinding.textView
        private val switch: SwitchCompat = itemBinding.actionSwitch
        var actionGroup: ActionGroup? = null

        fun setValues(){
            if(actionGroup != null){
                textView.text = actionGroup!!.title
                switch.isChecked = actionGroup!!.active
            }
        }

        init {
            switch.setOnClickListener {
                val isChecked = switch.isChecked
                if(isChecked){
                    switch.isChecked = !isChecked
                    val requiredPermissions =
                        ActionGroupTitle.valueOf(actionGroup!!.title.uppercase())
                            .getRequiredPermissions()
                    Dexter.withContext(context)
                        .withPermissions(
                            requiredPermissions
                        )
                        .withListener(PermissionListener(context) {
                            //method to be called if permissions are granted
                            updateElement(actionGroup!!, isChecked)
                        })
                        .check()
                } else {
                    updateElement(actionGroup!!, false)
                }
            }
        }

        private fun updateElement(actionGroup: ActionGroup, isChecked: Boolean){
            actionGroup.active = isChecked
            viewModel.updateActionGroup(actionGroup)
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