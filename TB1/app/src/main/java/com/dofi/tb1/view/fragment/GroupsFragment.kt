package com.dofi.tb1.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dofi.tb1.data.model.NetworkResultState
import com.dofi.tb1.databinding.FragmentGroupsBinding
import com.dofi.tb1.view.adapter.GroupsAdapter
import com.dofi.tb1.view.model.DummyApiViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GroupsFragment : Fragment() {

    private lateinit var binding: FragmentGroupsBinding
    private val viewModel by viewModel<DummyApiViewModel>()
    private val groupsAdapter by lazy { GroupsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getListOfUsers(listOf(50, 0))

        onViewListener()
        onObserverListener()
    }

    private fun onViewListener() = with(binding) {
        rvGroups.also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = groupsAdapter
        }
    }

    private fun onObserverListener() = with(binding) {
        viewModel.apply {
            listOfUsers.observe(viewLifecycleOwner) {
                when (it) {
                    is NetworkResultState.Loading -> {
                        pbGroupLoading.visibility = View.VISIBLE
                    }
                    is NetworkResultState.Success -> {
                        pbGroupLoading.visibility = View.GONE
                        groupsAdapter.setData(it.data?.data ?: emptyList())
                    }
                    else -> {}
                }
            }
        }
    }

}