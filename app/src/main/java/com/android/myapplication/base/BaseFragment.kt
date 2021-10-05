package com.android.myapplication.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint

abstract class BaseFragment<out VBinding: ViewBinding, IView : BaseContract.View, IPresenter : BaseContract.Presenter<IView>> : Fragment() {

    private lateinit var _binding: VBinding
    protected val binding: VBinding get() = _binding
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VBinding
    abstract val presenter: IPresenter

    abstract fun setup(view: View)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bindingInflater.invoke(layoutInflater, container, false)
        return _binding.root
    }

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(presenter) { attach(this@BaseFragment as? IView) }
        setup(view)
    }
}