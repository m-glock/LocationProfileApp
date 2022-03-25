package com.mglock.locationprofileapp.views.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mglock.locationprofileapp.R
import com.mglock.locationprofileapp.database.entities.relations.ProfileWithRelations
import com.mglock.locationprofileapp.databinding.ListTileProfilesBinding

class RecyclerViewProfilesAdapter(private val dataSet: List<ProfileWithRelations>) :
    RecyclerView.Adapter<RecyclerViewProfilesAdapter.ViewHolder>() {

    private var context: Context? = null

    inner class ViewHolder(itemBinding: ListTileProfilesBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private var activeProfileCheck: ImageView = itemBinding.activeProfileCheck
        private var profileTitle: TextView = itemBinding.profileTitle
        private var profileTimeText: TextView = itemBinding.profileTimeText
        private var profilePlaceText: TextView = itemBinding.profilePlaceText
        private var profileActionText: TextView = itemBinding.profileActionText

        lateinit var profileWithRelations: ProfileWithRelations

        fun setValues(){
            activeProfileCheck.visibility = if(profileWithRelations.profile.active) View.VISIBLE else View.INVISIBLE
            profileTitle.text = profileWithRelations.profile.title
            profileTimeText.text = "-" // TODO profile.timeframe
            profilePlaceText.text = profileWithRelations.place?.title ?: "-"
            profileActionText.text = "Bluetooth on, Volume 7/11" // TODO profile.actions
        }

        init {
            // Define click listener for the ViewHolder's View.
            itemBinding.buttonExpand.setOnClickListener {
                val height: Int
                val imageResource: Int
                if(itemBinding.expandableLayout.layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT){
                    height = context!!.resources.getDimensionPixelSize(R.dimen.profile_expandable_zero_height)
                    imageResource = R.drawable.ic_action_arrow_down
                } else {
                    height = ViewGroup.LayoutParams.WRAP_CONTENT
                    imageResource = R.drawable.ic_action_arrow_up
                }
                itemBinding.expandableLayout.layoutParams.height = height
                itemBinding.expandableLayout.requestLayout()
                itemBinding.buttonExpand.setImageResource(imageResource)
            }

            itemBinding.activateProfileButton.setOnClickListener {
                itemBinding.activateProfileButton.text =
                    if(itemBinding.activateProfileButton.text == "Activate"){
                        "Deactivate"
                    } else {
                        "Activate"
                    }
                profileWithRelations.profile.active = !profileWithRelations.profile.active //TODO update in RecyclerView
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
        context = parent.context

        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.profileWithRelations = dataSet[position]
        holder.setValues()
    }

    override fun getItemCount() = dataSet.size
}