package com.mglock.locationprofileapp.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mglock.locationprofileapp.R
import com.mglock.locationprofileapp.database.entities.Profile
import com.mglock.locationprofileapp.databinding.ListTileProfilesBinding

class RecyclerViewProfilesAdapter(private val dataSet: List<Profile>) :
    RecyclerView.Adapter<RecyclerViewProfilesAdapter.ViewHolder>() {

    class ViewHolder(itemBinding: ListTileProfilesBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private var activeProfileCheck: ImageView = itemBinding.activeProfileCheck
        private var profileTitle: TextView = itemBinding.profileTitle
        private var profileTimeText: TextView = itemBinding.profileTimeText
        private var profilePlaceText: TextView = itemBinding.profilePlaceText
        private var profileActionText: TextView = itemBinding.profileActionText

        lateinit var profile: Profile

        fun setValues(){
            activeProfileCheck.visibility = if(profile.active) View.VISIBLE else View.INVISIBLE
            profileTitle.text = profile.title
            profileTimeText.text = "-" // TODO profile.timeframe
            profilePlaceText.text = "Home" // TODO profile.place
            profileActionText.text = "Bluetooth on, Volume 7/11" // TODO profile.actions
        }

        init {
            // Define click listener for the ViewHolder's View.
            itemBinding.buttonExpand.setOnClickListener {
                val visibility: Int
                val imageResource: Int
                if(itemBinding.expandableLayout.visibility == View.GONE){
                    visibility = View.VISIBLE
                    imageResource = R.drawable.ic_action_arrow_up
                } else {
                    visibility = View.GONE
                    imageResource = R.drawable.ic_action_arrow_down
                }
                itemBinding.expandableLayout.visibility = visibility
                itemBinding.buttonExpand.setImageResource(imageResource)
            }

            itemBinding.activateProfileButton.setOnClickListener {
                itemBinding.activateProfileButton.text =
                    if(itemBinding.activateProfileButton.text == "Activate"){
                        "Deactivate"
                    } else {
                        "Activate"
                    }
                profile.active = !profile.active //TODO update in RecyclerView
            }

            itemBinding.editProfileButton.setOnClickListener {
                //TODO implement edit
            }

            itemBinding.deleteProfileButton.setOnClickListener {
                //TODO implement delete
            }
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