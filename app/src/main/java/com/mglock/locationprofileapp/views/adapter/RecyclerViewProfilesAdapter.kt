package com.mglock.locationprofileapp.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mglock.locationprofileapp.database.entities.Profile
import com.mglock.locationprofileapp.databinding.ListTileProfilesBinding

class RecyclerViewProfilesAdapter(private val dataSet: List<Profile>) :
    RecyclerView.Adapter<RecyclerViewProfilesAdapter.ViewHolder>() {

    class ViewHolder(itemBinding: ListTileProfilesBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private var activeProfileCheck: ImageView = itemBinding.activeProfileCheck
        private var profileTitle: TextView = itemBinding.profileTitle

        lateinit var profile: Profile

        fun setValues(){
            activeProfileCheck.visibility = if(profile.active) View.VISIBLE else View.INVISIBLE
            profileTitle.text = profile.title
        }

        init {
            // Define click listener for the ViewHolder's View.

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemBinding = ListTileProfilesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.profile = dataSet[position]
        holder.setValues()
    }

    override fun getItemCount() = dataSet.size
}