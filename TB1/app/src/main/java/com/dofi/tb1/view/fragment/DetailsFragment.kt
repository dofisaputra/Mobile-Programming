package com.dofi.tb1.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dofi.tb1.R
import com.dofi.tb1.data.model.DetailProfile
import com.dofi.tb1.databinding.FragmentDetailsBinding
import com.dofi.tb1.view.adapter.DetailsAdapter

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val detailsAdapter by lazy { DetailsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewListener()
        onObserverListener()
    }

    private fun onViewListener() = with(binding) {
        rvDetails.also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = detailsAdapter
        }
    }

    private fun onObserverListener() {
        detailsAdapter.setData(
            listOf(
                DetailProfile("About Me", "Hanya Seorang Pemuda yang sedang mencari pengalaman dan kenyamanan."),
            )
        )
    }

}