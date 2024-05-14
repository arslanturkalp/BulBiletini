package com.alparslanturk.bulbiletini.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.data.entities.models.MessageEvent
import com.alparslanturk.bulbiletini.databinding.ActivityMainBinding
import com.alparslanturk.bulbiletini.ui.favourites.FavouritesFragment
import com.alparslanturk.bulbiletini.ui.home.HomeFragment
import com.alparslanturk.bulbiletini.ui.messages.MessagesFragment
import com.alparslanturk.bulbiletini.ui.settings.SettingsFragment
import com.alparslanturk.bulbiletini.utils.addOnBackPressedListener
import com.alparslanturk.bulbiletini.utils.setSelectedTab
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val fragmentList: MutableList<Fragment> = mutableListOf()

    val homeFragment = HomeFragment()
    private val favouritesFragment = FavouritesFragment()
    private val messagesFragment = MessagesFragment()
    private val settingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initFragments()
        setupBottomNavigationView()

        showFragment(fragmentList.first())

        if (intent.getBooleanExtra(EXTRAS_DATA_CLICK_FAVOURITES, false)) {
            addOnBackPressedListener { finish() }
            clickFavourites()
        }
    }

    private fun initFragments() {
        fragmentList.apply {
            add(homeFragment)
            add(favouritesFragment)
            add(messagesFragment)
            add(settingsFragment)
        }
    }

    private fun setupBottomNavigationView() {
        with(binding.bottomNavigationView) {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_home -> {
                        showFragment(homeFragment)
                        true
                    }
                    R.id.navigation_favourites -> {
                        showFragment(favouritesFragment)
                        EventBus.getDefault().post(MessageEvent("Favourite Update"))
                        true
                    }
                    R.id.navigation_messages -> {
                        showFragment(messagesFragment)
                        EventBus.getDefault().post(MessageEvent("Messages Update"))
                        true
                    }
                    R.id.navigation_settings -> {
                        showFragment(settingsFragment)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    fun showFragment(selectedFragment: Fragment) {

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentList.forEach { fragment ->

            if (selectedFragment == fragment) {
                if (!selectedFragment.isAdded) {
                    fragmentTransaction.add(binding.fragmentContainerView.id, selectedFragment, getFragmentTag(selectedFragment))
                }
                fragmentTransaction.show(selectedFragment)

            } else {
                if (fragment.isAdded) {
                    fragmentTransaction.hide(fragment)
                }
            }
        }

        fragmentTransaction.commit()
    }

    fun setItemInNavigation(fragment: Fragment) {
        binding.bottomNavigationView.apply {
            if (fragment == homeFragment) {
                this.menu.getItem(0).isChecked = true
            }
            if (fragment == favouritesFragment) {
                this.menu.getItem(1).isChecked = true
            }
            if (fragment == messagesFragment) {
                this.menu.getItem(2).isChecked = true
            }
            if (fragment == settingsFragment) {
                this.menu.getItem(3).isChecked = true
            }
        }

    }

    private fun getFragmentTag(fragment: Fragment): String = fragment.javaClass.simpleName

    private fun clickFavourites() = binding.bottomNavigationView.setSelectedTab(R.id.navigation_favourites)

    @SuppressLint("NewApi")
    fun setNotificationBadge(count: Int) {
        binding.bottomNavigationView.apply {
            if (count > 0) {
                getOrCreateBadge(menu.getItem(2).itemId).apply {
                    number = count
                    verticalOffset = 20
                    backgroundColor = getColor(R.color.green)
                    badgeTextColor = getColor(R.color.white)
                }
            } else {
                removeBadge(2)
            }

        }
    }

    companion object {

        private const val EXTRAS_DATA_CLICK_FAVOURITES = "EXTRAS_DATA_CLICK_FAVOURITES"

        fun createIntent(context: Context?, isClickedFavourites: Boolean = false): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtra(EXTRAS_DATA_CLICK_FAVOURITES, isClickedFavourites)
            }
        }
    }
}