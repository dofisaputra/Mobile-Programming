package com.dofi.tb1.view.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dofi.tb1.R
import com.dofi.tb1.databinding.ActivityHomeBinding
import com.dofi.tb1.extension.getStringPref
import com.dofi.tb1.extension.putStringPref
import com.dofi.tb1.view.fragment.DiscoverPeopleFragment
import com.dofi.tb1.view.fragment.GroupsFragment
import com.dofi.tb1.view.fragment.HomeFragment
import com.dofi.tb1.view.fragment.ProfileFragment
import com.dofi.tb1.view.model.DummyApiViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel by viewModel<DummyApiViewModel>()
    private val userLogin by lazy { getStringPref("userLogin") }

    private val homeFragment by lazy {
        supportFragmentManager
            .findFragmentByTag(TAG_HOME) as? HomeFragment
            ?: HomeFragment()
    }
    private val discoverPeopleFragment by lazy {
        supportFragmentManager
            .findFragmentByTag(TAG_DISCOVER_PEOPLE) as? DiscoverPeopleFragment
            ?: DiscoverPeopleFragment()
    }
    private val groupsFragment by lazy {
        supportFragmentManager
            .findFragmentByTag(TAG_GROUPS) as? GroupsFragment
            ?: GroupsFragment()
    }
    private val profileFragment by lazy {
        supportFragmentManager
            .findFragmentByTag(TAG_PROFILE) as? ProfileFragment
            ?: ProfileFragment()
    }

    private var selectedFragment: Int = R.id.nav_home
    private var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savedInstanceState?.let {
            selectedFragment = it.getInt(CURRENT_FRAGMENT, R.id.nav_home)
        }

        when (selectedFragment) {
            R.id.nav_home -> activeFragment = homeFragment
            R.id.nav_discover_people -> activeFragment = discoverPeopleFragment
            R.id.nav_groups -> activeFragment = groupsFragment
            R.id.nav_profile -> activeFragment = profileFragment
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fl_content, homeFragment, TAG_HOME)
                .add(R.id.fl_content, discoverPeopleFragment, TAG_DISCOVER_PEOPLE)
                .add(R.id.fl_content, groupsFragment, TAG_GROUPS)
                .add(R.id.fl_content, profileFragment, TAG_PROFILE)
                .hide(discoverPeopleFragment)
                .hide(groupsFragment)
                .hide(profileFragment)
                .show(homeFragment)
                .commit()
        }

        setupView()

        if (userLogin.isEmpty() || userLogin == "") {
            val intent = Intent(this@HomeActivity, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CURRENT_FRAGMENT, selectedFragment)
    }

    private fun setupView() = with(binding) {
        bottomNavigation.setOnItemSelectedListener {
            setFragment(it.itemId)
        }
    }

    private fun setFragment(itemId: Int): Boolean {
        selectedFragment = itemId
        when (itemId) {
            R.id.nav_home -> {
                if (activeFragment is HomeFragment) return false
                supportFragmentManager.beginTransaction().hide(activeFragment!!)
                    .show(homeFragment).commit()

                activeFragment = homeFragment
            }

            R.id.nav_discover_people -> {
                if (activeFragment is DiscoverPeopleFragment) return false
                supportFragmentManager.beginTransaction().hide(activeFragment!!)
                    .show(discoverPeopleFragment).commit()

                activeFragment = discoverPeopleFragment
            }

            R.id.nav_groups -> {
                if (activeFragment is GroupsFragment) return false
                supportFragmentManager.beginTransaction().hide(activeFragment!!)
                    .show(groupsFragment).commit()

                activeFragment = groupsFragment
            }

            R.id.nav_profile -> {
                if (activeFragment is ProfileFragment) return false
                supportFragmentManager.beginTransaction().hide(activeFragment!!)
                    .show(profileFragment).commit()

                activeFragment = profileFragment
            }
        }
        return true
    }

    companion object {
        const val CURRENT_FRAGMENT = "current_fragment"
        const val TAG_HOME = "home"
        const val TAG_DISCOVER_PEOPLE = "discover_people"
        const val TAG_GROUPS = "groups"
        const val TAG_PROFILE = "profile"
    }

}