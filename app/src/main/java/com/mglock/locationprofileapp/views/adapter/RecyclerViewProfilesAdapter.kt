package com.mglock.locationprofileapp.views.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.mglock.locationprofileapp.R
import com.mglock.locationprofileapp.database.entities.DetailAction
import com.mglock.locationprofileapp.database.entities.Timeframe
import com.mglock.locationprofileapp.database.entities.relations.ProfileWithRelations
import com.mglock.locationprofileapp.databinding.ListTileProfilesBinding
import com.mglock.locationprofileapp.viewmodels.ProfilesViewModel

class RecyclerViewProfilesAdapter(
    private val dataSet: List<ProfileWithRelations>,
    private val viewModel: ProfilesViewModel,
    val adapterOnClick : (ProfileWithRelations) -> Unit
): RecyclerView.Adapter<RecyclerViewProfilesAdapter.ViewHolder>() {

    private var context: Context? = null

    inner class ViewHolder(val itemBinding: ListTileProfilesBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        lateinit var profile: ProfileWithRelations

        fun setValues(){
            itemBinding.activeProfileCheck.visibility = if(profile.profile.active) View.VISIBLE else View.INVISIBLE
            itemBinding.profileTitle.text = profile.profile.title
            itemBinding.profileTimeText.text = getStringFromTimeframe(profile.timeframe)
            itemBinding.profilePlaceText.text = profile.place?.title ?: "-"
            itemBinding.profileActionText.text = getStringFromActions(profile.actions)
            itemBinding.activateProfileButton.text = if(profile.profile.active) "Deactivate" else "Activate"
        }

        init {
            itemBinding.buttonExpand.setOnClickListener {
                expandDetailInfo(itemBinding.expandableLayout, itemBinding.buttonExpand)
            }

            itemBinding.activateProfileButton.setOnClickListener {
                profile.profile.active = !profile.profile.active
                viewModel.updateProfile(profile.profile)
            }

            itemBinding.editProfileButton.setOnClickListener {
                editProfile(profile)
            }

            itemBinding.deleteProfileButton.setOnClickListener {
                viewModel.deleteProfile(profile.profile)
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
        holder.profile = dataSet[position]
        holder.setValues()
    }

    override fun getItemCount() = dataSet.size

    private fun expandDetailInfo(expandableLayout: ConstraintLayout, expandableButton: ImageButton){
        if(expandableLayout.layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT){
            expandableLayout.layoutParams.height =
                context!!.resources.getDimensionPixelSize(R.dimen.profile_expandable_zero_height)
            expandableButton.setImageResource(R.drawable.ic_action_arrow_down)
        } else {
            expandableLayout.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            expandableButton.setImageResource(R.drawable.ic_action_arrow_up)
        }
        expandableLayout.requestLayout()
    }

    private fun editProfile(profile: ProfileWithRelations){
        adapterOnClick(profile)
    }

    private fun getStringFromTimeframe(timeframe: Timeframe?): String{
        if(timeframe == null) return "-"
        return timeframe.toString()
    }

    private fun getStringFromActions(actions: List<DetailAction>): String{
        if(actions.isEmpty()) return "-"
        return actions.joinToString(","){ action -> action.title.toString()}
    }
}