package com.social.presentation.menu

import android.view.LayoutInflater
import android.view.View
import com.social.data.models.MenuModel
import com.social.data.models.ProfileModel
import com.social.databinding.ActivityMenuSearchBinding
import com.social.presentation.base.BaseActivity
import com.social.presentation.profile.ProfileViewModel
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.social.hilt.RecyclerViewModule.Companion.ACTIVITY_SCOPED
import com.social.presentation.menu.adapter.SearchMenuAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class ActivityMenuSearch : BaseActivity<ActivityMenuSearchBinding>(), SearchView.OnQueryTextListener {

    private var menuCategory: Int = 0

    override val bindingInflater: (LayoutInflater) -> ActivityMenuSearchBinding
        get() = ActivityMenuSearchBinding::inflate
    @Inject
    lateinit var searchMenuAdapter: SearchMenuAdapter

    @Inject
    @Named(ACTIVITY_SCOPED)
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var itemAnimator: DefaultItemAnimator

    private val menuViewModel: MenuViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    private lateinit var user: ProfileModel

    override fun setup() {

        menuCategory = intent?.getIntExtra(MenuModel.CATEGORY, 0) ?: 0

        binding.ibActionBack.setOnClickListener {
            finish()
        }



        searchMenuAdapter.fragmentManager = supportFragmentManager
        binding.rvMenu.layoutManager = linearLayoutManager
        binding.rvMenu.itemAnimator = itemAnimator
        binding.rvMenu.adapter = searchMenuAdapter
        binding.etSearchMenu.setOnQueryTextListener(this)

    }

    override fun fetchData() {
        profileViewModel.getCachedUser(true)
    }

    override fun collectViewModels() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    menuViewModel.uiState.collect {
                        when (it) {
                            is MenuViewModel.UiState.Loading -> {
                                showLoading(true)
                                showNoResults(false)
                            }
                            is MenuViewModel.Success.Menu -> {
                                searchMenuAdapter.submitData(it.menu)
                                showNoResults(false)
                            }
                            else -> {
                                showNoResults(true)
                                showLoading(false)
                            }
                        }
                    }
                }
                launch {
                    profileViewModel.cachedUserState.collect {
                        when (it) {
                            is ProfileViewModel.StateSuccess.CachedUser -> {
                                user = it.user
                                onQueryTextSubmit("")
                            }
                            else -> showNoResults(true)
                        }
                    }
                }
            }
        }
    }

    private fun showNoResults(show: Boolean){
        binding.tvSearchMenuNoResult.visibility = if(show) View.VISIBLE else View.GONE
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        menuViewModel.getMenu(user.id, menuCategory, query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        menuViewModel.getMenu(user.id, menuCategory, newText)
        return true
    }
}