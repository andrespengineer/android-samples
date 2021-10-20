package com.social.presentation.fragments

import android.view.*
import com.social.presentation.base.BaseFragment
import com.social.databinding.FragmentChatBinding
import com.social.presentation.adapters.ChatMessagesAdapter
import com.social.presentation.viewmodels.ChatMessagesViewModel
import com.social.presentation.viewmodels.ProfileViewModel
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.social.R
import com.social.hilt.RecyclerViewModule
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class FragmentChat : BaseFragment<FragmentChatBinding>() {

    @Inject
    @Named(RecyclerViewModule.LAYOUT_MANAGER_VERTICAL_REVERSED)
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var chatAdapter: ChatMessagesAdapter

    @Inject
    lateinit var itemAnimator: DefaultItemAnimator

    private val profileViewModel: ProfileViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    private val chatMessagesViewModel: ChatMessagesViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    private val recyclerViewScrollListener = object: RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_IDLE){
                if(linearLayoutManager.findFirstVisibleItemPosition() <= 2){
                    binding.ibScrollToBottom.visibility = View.GONE
                } else {
                    binding.ibScrollToBottom.visibility = View.VISIBLE
                }
            }
            if(newState == RecyclerView.SCROLL_STATE_DRAGGING){
                binding.ibScrollToBottom.visibility = View.GONE
            }
        }
    }

    private var recyclerView : RecyclerView? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentChatBinding
        get() = FragmentChatBinding::inflate

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView = null
    }

    override fun setup() {

        binding.rvChatMessagesList.layoutManager = linearLayoutManager
        binding.rvChatMessagesList.removeOnScrollListener(recyclerViewScrollListener)
        binding.rvChatMessagesList.addOnScrollListener(recyclerViewScrollListener)
        binding.rvChatMessagesList.itemAnimator = itemAnimator
        binding.rvChatMessagesList.adapter = chatAdapter

        binding.ibScrollToBottom.setOnClickListener {
            binding.rvChatMessagesList.scrollToPosition(0)
        }

        binding.btnChatSend.setOnClickListener {
            val message: String = binding.etChatInput.text.toString()
            if (message.trim().isNotEmpty()) {
                binding.etChatInput.setText("")
            }
        }
    }

    override fun collectViewModels() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    chatMessagesViewModel.uiState.collect {
                        when (it) {
                            is ChatMessagesViewModel.UiState.Loading -> showLoading(true)
                            is ChatMessagesViewModel.StateSuccess.Messages -> launch {
                                chatAdapter.submitData(
                                    it.data
                                )
                            }
                            else -> {
                                showLoading(false)
                            }
                        }
                    }
                }

                launch {
                    profileViewModel.cachedUserState.collect {
                        when (it) {
                            is ProfileViewModel.StateSuccess.CachedUser -> {
                                chatAdapter.user = it.user
                                chatMessagesViewModel.getMessages(userId = it.user.id)
                            }
                            else -> showLoading(false)
                        }
                    }
                }
            }
        }
    }

    override fun fetchData() {
        profileViewModel.getCachedUser()
    }

    override fun showLoading(show: Boolean) {

    }

    override fun onRefresh() {
        profileViewModel.getCachedUser(true)
    }

}