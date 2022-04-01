package com.mglock.locationprofileapp.views.profiles.fragments

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
import com.mglock.locationprofileapp.databinding.FragmentAddDetailActionBinding
import com.mglock.locationprofileapp.util.enums.DetailActionOption
import com.mglock.locationprofileapp.util.phonefunctionality.BluetoothHandler
import com.mglock.locationprofileapp.viewmodels.profiles.AddDetailActionViewModel

class AddDetailActionFragment(private val profileId: Long) : DialogFragment() {

    private lateinit var mViewModel: AddDetailActionViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mViewModel = ViewModelProvider(this)[AddDetailActionViewModel::class.java]
        val actionOptions = DetailActionOption.values()

        val binding = FragmentAddDetailActionBinding.inflate(LayoutInflater.from(context))

        // show correct fragment for value selection if something in the dropdown is chosen
        binding.actionDropdown.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val option = actionOptions[pos]
                if(option == DetailActionOption.NOTIFY_BLUETOOTH_DEVICE_CONNECTED
                    && !BluetoothHandler(requireContext()).checkIfBluetoothEnabled()){
                    AlertDialog.Builder(requireContext())
                        .setTitle("Bluetooth not enabled.")
                        .setMessage("Please (temporarily) enable bluetooth so that " +
                                "the app can access the list of paired devices.")
                        .setPositiveButton("Understood", null)
                        .show()
                    binding.actionDropdown.setSelection(0)
                } else {
                    startFragment(binding, option)
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
            return AlertDialog.Builder(fragmentActivity)
                .setTitle("Title")
                .setView(view)
                .setPositiveButton("Add"){ _, _ ->
                    val valueFragment = childFragmentManager.fragments[0] as? BaseDetailActionFragment
                    if(valueFragment != null){
                        val selectedAction = binding.actionDropdown.selectedItem as String
                        val detailActionTitle = DetailActionOption.values().find { option -> option.title == selectedAction }
                        val selectedValue = valueFragment.getValue()
                        val profileIdForAction = if(profileId > 0) profileId else null
                        mViewModel.addAction(
                            DetailAction(0, profileIdForAction, detailActionTitle!!, selectedValue),
                            requireContext()
                        )
                    }
                }.setNegativeButton("Back"){ _, _ -> }.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun startFragment(binding: FragmentAddDetailActionBinding, option: DetailActionOption){
        val fragment = DetailActionOption.getValueSelectionFragment(option)
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.actionValueFragment.id, fragment)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction.commit()
    }

}