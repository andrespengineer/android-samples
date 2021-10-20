package com.social.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.social.presentation.common.CommonActions
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<out binding: ViewBinding?> : Fragment(), CommonActions {

    private var _binding: binding? = null

    protected val binding: binding get() = _binding!!

    private var cachedView : View? = null

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> binding

    abstract fun setup()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(cachedView == null) {
            _binding = bindingInflater.invoke(layoutInflater, container, false)
            cachedView = _binding?.root
        }
        return cachedView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        collectViewModels()
        fetchData()
    }

    /***
     * Request Permissions if Any activity level permissions revoked
     */
    override fun requestPermissions() {
        // Not implemented
    }

    open fun onRefresh(){ /* Default impl */}

}
