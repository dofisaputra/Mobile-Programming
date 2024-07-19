package com.dofi.tb1.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dofi.tb1.data.model.NetworkResultState
import com.dofi.tb1.databinding.FragmentSignInBinding
import com.dofi.tb1.extension.putStringPref
import com.dofi.tb1.view.activity.HomeActivity
import com.dofi.tb1.view.model.DummyApiViewModel
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private val viewModel by activityViewModel<DummyApiViewModel>()
    private val gson by inject<Gson>()

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
        onObserverListener()
    }

    private fun onViewListener() = with(binding) {
        btnSignIn.setOnClickListener {
            viewModel.getListOfUsers(listOf(50, 0))
        }
    }

    private fun onObserverListener() = with(binding) {
        viewModel.apply {
            listOfUsers.observe(viewLifecycleOwner) {
                when (it) {
                    is NetworkResultState.Success -> {
                        val email = binding.tieEmailPhone.text.toString()
                        val password = binding.tiePassword.text.toString()

                        it.data?.data?.let { users ->
                            val user = users.find { u ->
                                (u.firstName?.split("_||_")?.get(1)
                                    ?: "") == email && u.lastName == password
                            }
                            if (user != null) {
                                viewModel.getUserById(user.id.toString())
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Invalid email or password",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    else -> {}
                }
            }

            getUserByIdResponse.observe(viewLifecycleOwner) {
                when (it) {
                    is NetworkResultState.Success -> {
                        requireContext().putStringPref("userLogin", gson.toJson(it.data))
                        val intent = Intent(requireContext(), HomeActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }

                    else -> {}
                }
            }
        }
    }

}