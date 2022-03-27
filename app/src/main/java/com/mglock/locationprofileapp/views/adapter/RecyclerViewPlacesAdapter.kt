package com.mglock.locationprofileapp.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mglock.locationprofileapp.database.entities.Place
import com.mglock.locationprofileapp.databinding.ListTilePlacesBinding
import com.mglock.locationprofileapp.viewmodels.PlacesViewModel

class RecyclerViewPlacesAdapter(private val dataSet: List<Place>, private val viewModel: PlacesViewModel) :
    RecyclerView.Adapter<RecyclerViewPlacesAdapter.ViewHolder>() {

    inner class ViewHolder(itemBinding: ListTilePlacesBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private val placeTitle: TextView = itemBinding.placeTitle
        private val placeAddress: TextView = itemBinding.placeAddress
        var place: Place? = null

        fun setValues(){
            if(place != null){
                placeTitle.text = place!!.title
                placeAddress.text = place!!.address //TODO either address or lat/long
            }
        }

        init {
            itemBinding.buttonDelete.setOnClickListener {
                viewModel.deletePlace(place!!)
            }
            itemBinding.buttonEdit.setOnClickListener {
                //TODO edit item
                viewModel.updatePlace(place!!)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemBinding = ListTilePlacesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.place = dataSet[position]
        holder.setValues()
    }

    override fun getItemCount() = dataSet.size
}