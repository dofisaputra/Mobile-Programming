package com.dofi.tb1.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dofi.tb1.R
import com.dofi.tb1.databinding.FragmentDiscoverPeopleBinding
import com.dofi.tb1.view.adapter.FollowAdapter
import com.dofi.tb1.view.model.DummyApiViewModel
import com.dofi.tb1.view.model.FetchType
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class DiscoverPeopleFragment : Fragment() {

    private lateinit var binding: FragmentDiscoverPeopleBinding
    private val viewModel by activityViewModel<DummyApiViewModel>()
    private val followAdapter by lazy { FollowAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiscoverPeopleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchUsers()

        onViewListener()
        onObserverListener()
    }

    private fun onViewListener() = with(binding) {
        rvFollow.also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = followAdapter
        }
    }

    private fun onObserverListener() = with(binding) {
        viewModel.apply {
            loadingState.observe(viewLifecycleOwner) { state ->
                state[FetchType.USERS]?.let {
                    pbUserLoading.visibility = if (it) View.VISIBLE else View.GONE
                }
            }

            users.observe(viewLifecycleOwner) { response ->
                response.data?.let { user ->
                    followAdapter.setData(user)
                }
            }
        }
    }

}