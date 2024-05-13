package com.dofi.tb1.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dofi.tb1.databinding.ActivitySignInBinding
import com.dofi.tb1.view.fragment.SignInFragment
import com.dofi.tb1.view.fragment.SignUpFragment
import com.google.android.material.tabs.TabLayout

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val signInFragment by lazy { SignInFragment() }
    private val signUpFragment by lazy { SignUpFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() = with(binding) {
        commitFragment(signInFragment)

        tlSignInUp.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> commitFragment(signInFragment)
                    1 -> commitFragment(signUpFragment)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun commitFragment(fragment: Fragment) = with(binding) {
        supportFragmentManager.beginTransaction()
            .replace(flContent.id, fragment)
            .commit()
    }
}