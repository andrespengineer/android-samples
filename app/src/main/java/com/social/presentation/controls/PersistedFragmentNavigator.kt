package com.social.presentation.controls

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import com.social.presentation.controls.PersistedFragmentNavigator.Companion.PERSISTED_FRAGMENT

@Navigator.Name(PERSISTED_FRAGMENT)
class PersistedFragmentNavigator (private val context: Context, private val fragmentManager: FragmentManager, private val containerId: Int)
    : FragmentNavigator(context, fragmentManager, containerId) {

    companion object {
        private const val TAG = "PersistedFragmentNavigator"
        const val PERSISTED_FRAGMENT = "persisted_fragment"
    }

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {

        if (fragmentManager.isStateSaved) {
            Log.i(
                TAG, "Ignoring navigate() call: FragmentManager has already"
                        + " saved its state"
            )
            return null
        }

        var className = destination.className
        if (className[0] == '.') {
            className = context.packageName + className
        }

        val fragmentTransaction = fragmentManager.beginTransaction()

        var enterAnim = navOptions?.enterAnim ?: -1
        var exitAnim = navOptions?.exitAnim ?: -1
        var popEnterAnim = navOptions?.popEnterAnim ?: -1
        var popExitAnim = navOptions?.popExitAnim ?: -1

        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = if (enterAnim != -1) enterAnim else 0
            exitAnim = if (exitAnim != -1) exitAnim else 0
            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
            fragmentTransaction.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }

        var initialFragment = false

        if(fragmentManager.primaryNavigationFragment == null){
            initialFragment = true
        }
        else {
            if(fragmentManager.primaryNavigationFragment?.tag == destination.className)
                return null
            else
                fragmentTransaction.detach(fragmentManager.primaryNavigationFragment!!)
        }

        var fragment: Fragment? = fragmentManager.findFragmentByTag(destination.className)

        if (fragment == null) {
            fragment = fragmentManager.fragmentFactory.instantiate(
                context.classLoader,
                className
            )
            fragment.arguments = args
            fragmentTransaction.add(containerId, fragment, destination.className)
        }
        else
            fragmentTransaction.attach(fragment)

        fragmentTransaction.addToBackStack(destination.className)
        fragmentTransaction.setPrimaryNavigationFragment(fragment)

        if (navigatorExtras is Extras) {
            for ((key, value) in navigatorExtras.sharedElements) {
                fragmentTransaction.addSharedElement(key!!, value!!)
            }
        }
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.commit()


        return if(initialFragment){
            destination
        } else null
    }
}