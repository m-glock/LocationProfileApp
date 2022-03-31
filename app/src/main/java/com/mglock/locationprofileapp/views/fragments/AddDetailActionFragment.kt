package com.mglock.locationprofileapp.views.fragments

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import com.mglock.locationprofileapp.R
import com.mglock.locationprofileapp.database.entities.DetailAction
import com.mglock.locationprofileapp.databinding.AddDetailActionFragmentBinding
import com.mglock.locationprofileapp.util.enums.DetailActionOption
import com.mglock.locationprofileapp.viewmodels.AddDetailActionViewModel

class AddDetailActionFragment : DialogFragment() {

    private lateinit var mViewModel: AddDetailActionViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mViewModel = ViewModelProvider(this)[AddDetailActionViewModel::class.java]
        val actionOptions = DetailActionOption.values()

        val binding = AddDetailActionFragmentBinding.inflate(LayoutInflater.from(context))

        // show correct fragment for value selection if something in the dropdown is chosen
        binding.actionDropdown.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val selectedItem = actionOptions[pos]
                if(selectedItem == DetailActionOption.CHANGE_VOLUME_MODE) {
                    val fragment = DetailActionOption.getValueSelectionFragment(selectedItem)
                    val fragmentTransaction = childFragmentManager.beginTransaction()
                    fragmentTransaction.replace(binding.actionValueFragment.id, fragment)
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    fragmentTransaction.commit()
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        binding.actionDropdown.adapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_item,
            actionOptions.map { enum -> enum.title }
        )
        val view = binding.root

        return activity?.let { fragmentActivity ->
            // Use the Builder class for convenient dialog construction
            return AlertDialog.Builder(fragmentActivity)
                .setTitle("Title")
                .setView(view)
                .setPositiveButton("yes"){ _, _ ->
                    val valueFragment = childFragmentManager.fragments[0] as? AddActionValueDropdownFragment
                    if(valueFragment != null){
                        val selectedAction = binding.actionDropdown.selectedItem as String
                        val detailActionTitle = DetailActionOption.valueOf(
                            selectedAction.replace(" ", "_").uppercase()
                        )
                        val selectedValue = valueFragment.getDropdownValue()
                        mViewModel.addAction(DetailAction(0, null, detailActionTitle, selectedValue))
                    }
                }.setNegativeButton("no"){ _, _ ->

                }.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}