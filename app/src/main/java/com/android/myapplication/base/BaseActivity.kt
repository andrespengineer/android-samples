package com.android.myapplication.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<out VBinding: ViewBinding, IView : BaseContract.View, IPresenter : BaseContract.Presenter<IView>> : AppCompatActivity() {

    // ViewBinding
    private lateinit var _binding: VBinding
    private val binding: VBinding get() = _binding
    abstract val bindingInflater: (LayoutInflater) -> VBinding

    abstract val presenter: IPresenter

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)

        with(presenter) {
            attach(view = this@BaseActivity as? IView)
        }
        setup()
    }

    abstract fun setup()
}