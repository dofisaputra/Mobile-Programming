package com.dofi.tb1.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dofi.tb1.databinding.FragmentSignInBinding
import com.dofi.tb1.view.activity.HomeActivity


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewListener()
    }

    private fun onViewListener() = with(binding) {
        btnSignIn.setOnClickListener{
            val intent = Intent(context, HomeActivity::class.java)
            startActivity(intent)
        }
    }

}