package com.mglock.locationprofileapp.views.fragments

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.mglock.locationprofileapp.R
import com.mglock.locationprofileapp.viewmodels.AddDetailActionViewModel

class AddDetailActionFragment : DialogFragment() {

    private lateinit var mViewModel: AddDetailActionViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mViewModel = ViewModelProvider(this)[AddDetailActionViewModel::class.java]

        // TODO create view and binding here to populate infos

        return activity?.let { fragmentActivity ->
            // Use the Builder class for convenient dialog construction
            return AlertDialog.Builder(fragmentActivity)
                .setTitle("Title")
                .setView(R.layout.add_detail_action_fragment)
                .setPositiveButton("yes"){ _, _ ->

                }.setNegativeButton("no"){ _, _ ->

                }.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}