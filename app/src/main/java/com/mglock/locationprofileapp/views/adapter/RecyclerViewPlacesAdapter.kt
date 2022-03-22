package com.mglock.locationprofileapp.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mglock.locationprofileapp.R
import com.mglock.locationprofileapp.database.entities.Place

class RecyclerViewPlacesAdapter(private val dataSet: List<Place>) :
    RecyclerView.Adapter<RecyclerViewPlacesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeTitle: TextView = view.findViewById(R.id.placeTitle)
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
        var place: Place? = null

        fun setValues(){
            if(place != null){
                placeTitle.text = place!!.title
                placeAddress.text = place!!.address //TODO either address or lat/long
            }
        }

        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_tile_places, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.place = dataSet[position]
        holder.setValues()
    }

    override fun getItemCount() = dataSet.size
}