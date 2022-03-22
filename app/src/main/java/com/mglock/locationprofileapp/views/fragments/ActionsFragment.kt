package com.mglock.locationprofileapp.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.mglock.locationprofileapp.databinding.FragmentActionsBinding
import com.mglock.locationprofileapp.viewmodels.ActionsViewModel
import com.mglock.locationprofileapp.views.adapter.RecyclerViewAdapter

class ActionsFragment : Fragment() {
    private var _binding: FragmentActionsBinding? = null
    private val binding get(): FragmentActionsBinding = _binding!!
    private lateinit var mViewModel: ActionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this)[ActionsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentActionsBinding.inflate(inflater, container, false)

        // set the adapter for the recyclerview to display the list items
        val recyclerView = _binding!!.recyclerViewActions
        mViewModel.actionGroups.observe(viewLifecycleOwner) { actionGroups ->
            recyclerView.adapter = RecyclerViewAdapter(actionGroups)
        }

        return binding.root
    }

    // called when the user switches to another tab
    // update the database
    override fun onPause() {
        super.onPause()
        mViewModel.updateActionGroup()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel.updateActionGroup()
        _binding = null
    }
}