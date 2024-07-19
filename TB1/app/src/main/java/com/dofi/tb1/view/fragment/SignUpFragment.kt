package com.dofi.tb1.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dofi.tb1.data.model.NetworkResultState
import com.dofi.tb1.data.model.user.User
import com.dofi.tb1.databinding.FragmentSignUpBinding
import com.dofi.tb1.view.model.DummyApiViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel by activityViewModel<DummyApiViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onViewListener()
        onObserverListener()
    }

    private fun onViewListener() = with(binding) {
        btnSignIn.setOnClickListener {
            val email = tieEmailPhone.text.toString()
            val fullName = tieFullName.text.toString()
            val password = tiePassword.text.toString()

            if (email.isEmpty() || fullName.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val userData = User(
                    firstName = "${fullName}_||_${email}",
                    lastName = password,
                    email = email,
                )

                viewModel.createUser(userData)
            }
        }
    }

    private fun onObserverListener() = with(binding) {
        viewModel.apply {
            createUserResponse.observe(viewLifecycleOwner) {
                when (it) {
                    is NetworkResultState.Success -> {
                        Toast.makeText(requireContext(), "User created, please Sign in", Toast.LENGTH_SHORT).show()
                    }

                    else -> {}
                }
            }
        }
    }

}