package com.dofi.tb1.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.dofi.tb1.data.model.getFullNames
import com.dofi.tb1.databinding.FragmentProfileBinding
import com.dofi.tb1.view.model.DummyApiViewModel
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by activityViewModel<DummyApiViewModel>()
    private val postFragment by lazy { PostFragment() }
    private val detailsFragment by lazy { DetailsFragment() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchLoginUser()
        viewModel.fetchPostByUser()

        onViewListener()
        onObserverListener()
    }

    private fun onViewListener() = with(binding) {
        commitFragment(postFragment)
        tlPostDetails.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> commitFragment(postFragment)
                    1 -> commitFragment(detailsFragment)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun onObserverListener() = with(binding) {
        viewModel.apply {
            loginUser.observe(viewLifecycleOwner) { response ->
                response?.let {
                    ivProfileBackground.load(it.picture)
                    ivProfileImage.load(it.picture)
                    tvUsername.text = it.getFullNames()
                    tvAccount.text = it.email
                    tvJob.text = it.location?.street
                    tvPost.text = (0..100).random().toString()
                    tvPhotos.text = (0..100).random().toString()
                    tvFollowers.text = (0..100).random().toString()
                    tvFollowing.text = (0..100).random().toString()
                }
            }
        }
    }

    private fun commitFragment(fragment: Fragment) = with(binding) {
        childFragmentManager.beginTransaction()
            .replace(flContent.id, fragment)
            .commit()
    }

}