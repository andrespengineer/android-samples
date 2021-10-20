package com.social.presentation.controls

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class RetainedNavigationHostFragment : NavHostFragment() {
    override fun onCreateNavController(navController: NavController) {
        super.onCreateNavController(navController)
        navController.navigatorProvider.addNavigator(PersistedFragmentNavigator(requireContext(), childFragmentManager, id))
    }
}