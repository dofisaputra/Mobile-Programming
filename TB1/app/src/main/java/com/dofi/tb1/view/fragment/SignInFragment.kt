package com.dofi.tb1.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dofi.tb1.data.model.UserLogin
import com.dofi.tb1.databinding.FragmentSignInBinding
import com.dofi.tb1.view.activity.HomeActivity
import com.dofi.tb1.view.model.UserApiViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private val viewModel by activityViewModel<UserApiViewModel>()

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
        btnSignIn.setOnClickListener{
            val userData = UserLogin(
                emailOrPhone = tieEmailPhone.text.toString(),
                password = tiePassword.text.toString()
            )

            viewModel.getUser(userData)
        }
    }

    private fun onObserverListener() = with(binding) {
        viewModel.apply {
            getUserResponse.observe(viewLifecycleOwner) {
                val password = tiePassword.text.toString()
                if (it != null) {
                    if (it.password == password) {
                        val intent = Intent(requireContext(), HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(requireContext(), "Password is incorrect", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}