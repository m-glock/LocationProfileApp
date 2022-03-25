package com.mglock.locationprofileapp.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.mglock.locationprofileapp.databinding.FragmentActionsBinding
import com.mglock.locationprofileapp.viewmodels.ActionsViewModel
import com.mglock.locationprofileapp.views.adapter.RecyclerViewActionsAdapter

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
            recyclerView.adapter = RecyclerViewActionsAdapter(actionGroups, mViewModel)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}