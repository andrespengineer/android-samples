package com.social.presentation.home.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.FragmentManager
import androidx.navigation.*
import androidx.navigation.NavController.OnDestinationChangedListener
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.social.R
import com.social.presentation.base.BaseActivity
import com.social.databinding.ActivityHomeBinding
import com.social.presentation.base.BaseFragment
import com.social.presentation.chat.adapter.fragments.FragmentChat
import com.social.presentation.controls.CachedFragmentFactory
import com.social.presentation.feed.fragments.FragmentFeed
import com.social.presentation.menu.fragments.FragmentMenu
import com.social.presentation.playlist.fragments.FragmentPlaylist
import com.social.presentation.profile.fragments.FragmentProfile
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityHome : BaseActivity<ActivityHomeBinding>(), OnDestinationChangedListener, FragmentManager.OnBackStackChangedListener {

    private lateinit var navController: NavController
    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate

    private val cachedFragmentFactory = CachedFragmentFactory()

    private lateinit var navHostFragment : NavHostFragment

    private val bottomNavigationFragments = hashMapOf(R.id.fragment_feed to FragmentFeed::class.qualifiedName,
        R.id.fragment_chat to FragmentChat::class.qualifiedName,
        R.id.fragment_playlist to FragmentPlaylist::class.qualifiedName,
        R.id.fragment_menu to FragmentMenu::class.qualifiedName,
        R.id.fragment_profile to FragmentProfile::class.qualifiedName)

    override fun setup() {

        binding.srlRefresh.setOnRefreshListener(this)

        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navigationHost) as NavHostFragment

        cachedFragmentFactory.fragmentSet = bottomNavigationFragments.values.toSet()

        navHostFragment.childFragmentManager.fragmentFactory = cachedFragmentFactory
        navHostFragment.childFragmentManager.removeOnBackStackChangedListener(this)
        navHostFragment.childFragmentManager.addOnBackStackChangedListener(this)

        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener(this)

        getCurrentChildFragmentManager().fragmentFactory = cachedFragmentFactory

        binding.bottomNavigation.setupWithNavController(navController)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
    }

    override fun onBackStackChanged() {
        cachedFragmentFactory.cachedFragments.entries.removeIf {
            val fragment = getCurrentChildFragmentManager().findFragmentById(it.value?.id ?: 0)
            fragment == null
        }
    }


    private fun getCurrentChildFragmentManager() = navHostFragment.childFragmentManager
    private fun getCurrentFragment() = getCurrentChildFragmentManager().primaryNavigationFragment

    override fun onRefresh() {
        super.onRefresh()

        val currentFragment = getCurrentFragment() as? BaseFragment<*>
        currentFragment?.onRefresh()

        binding.srlRefresh.postDelayed({
            binding.srlRefresh.isRefreshing = false
        }, 500)

    }
}