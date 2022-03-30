package com.mglock.locationprofileapp.views.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.karumi.dexter.Dexter
import com.mglock.locationprofileapp.database.entities.DetailAction
import com.mglock.locationprofileapp.databinding.ListTileActionsBinding
import com.mglock.locationprofileapp.util.PermissionListener
import com.mglock.locationprofileapp.util.enums.DetailActionTitle
import com.mglock.locationprofileapp.viewmodels.ActionsViewModel

class RecyclerViewActionsAdapter(
    private val dataSet: List<DetailAction>,
    private val viewModel: ActionsViewModel,
    private val context: Context
) : RecyclerView.Adapter<RecyclerViewActionsAdapter.ViewHolder>() {

    inner class ViewHolder(itemBinding: ListTileActionsBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private val textView: TextView = itemBinding.textView
        private val switch: SwitchCompat = itemBinding.actionSwitch
        var detailAction: DetailAction? = null

        fun setValues(){
            if(detailAction != null){
                textView.text = detailAction!!.title
                switch.isChecked = detailAction!!.active
            }
        }

        init {
            switch.setOnClickListener {
                val isChecked = switch.isChecked
                if(isChecked){
                    switch.isChecked = !isChecked
                    val requiredPermissions =
                        DetailActionTitle.valueOf(detailAction!!.title.uppercase())
                            .getRequiredPermissions()
                    Dexter.withContext(context)
                        .withPermissions(
                            requiredPermissions
                        )
                        .withListener(PermissionListener(context) {
                            //method to be called if permissions are granted
                            updateElement(detailAction!!, isChecked)
                        })
                        .check()
                } else {
                    updateElement(detailAction!!, false)
                }
            }
        }

        private fun updateElement(detailAction: DetailAction, isChecked: Boolean){
            detailAction.active = isChecked
            viewModel.updateDetailAction(detailAction)
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
        holder.detailAction = dataSet[position]
        holder.setValues()
    }

    override fun getItemCount() = dataSet.size
}