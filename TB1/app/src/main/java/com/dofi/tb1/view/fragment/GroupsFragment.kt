package com.dofi.tb1.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dofi.tb1.databinding.FragmentGroupsBinding
import com.dofi.tb1.view.adapter.GroupsAdapter
import com.dofi.tb1.view.model.DummyApiViewModel
import com.dofi.tb1.view.model.FetchType
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
        viewModel.fetchUsers()

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
            loadingState.observe(viewLifecycleOwner) { state ->
                state[FetchType.USERS]?.let {
                    pbGroupLoading.visibility = if (it) View.VISIBLE else View.GONE
                }
            }

            users.observe(viewLifecycleOwner) { response ->
                response.data?.let { user ->
                    groupsAdapter.setData(user)
                }
            }
        }
    }

}