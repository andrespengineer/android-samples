package com.social.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.viewbinding.ViewBinding

abstract class BaseDialogFragment<out binding: ViewBinding> : AppCompatDialogFragment() {

    private lateinit var _binding: binding
    protected val binding: binding get() = _binding
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> binding

    abstract fun setup()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bindingInflater.invoke(layoutInflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }
}
