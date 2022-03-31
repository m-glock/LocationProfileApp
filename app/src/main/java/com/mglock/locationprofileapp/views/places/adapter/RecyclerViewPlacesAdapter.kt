package com.mglock.locationprofileapp.views.places.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mglock.locationprofileapp.database.entities.Place
import com.mglock.locationprofileapp.databinding.ListTilePlacesBinding
import com.mglock.locationprofileapp.viewmodels.places.PlacesViewModel

class RecyclerViewPlacesAdapter(private val dataSet: List<Place>, private val viewModel: PlacesViewModel, val adapterOnClick : (Place) -> Unit) :
    RecyclerView.Adapter<RecyclerViewPlacesAdapter.ViewHolder>() {

    inner class ViewHolder(itemBinding: ListTilePlacesBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private val placeTitle: TextView = itemBinding.placeTitle
        private val placeAddress: TextView = itemBinding.placeAddress
        var place: Place? = null

        fun setValues(){
            if(place != null){
                placeTitle.text = place!!.title
                placeAddress.text = place!!.address ?: "${place!!.latitude}/${place!!.longitude}"
            }
        }

        init {
            itemBinding.buttonDelete.setOnClickListener {
                viewModel.deletePlace(place!!)
            }
            itemBinding.buttonEdit.setOnClickListener {
                adapterOnClick(place!!)
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