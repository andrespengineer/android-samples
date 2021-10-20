package com.social.presentation.base

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import com.social.presentation.common.CommonActions
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<out binding: ViewBinding> : AppCompatActivity(), CommonActions, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var _binding: binding
    protected val binding: binding get() = _binding
    abstract val bindingInflater: (LayoutInflater) -> binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)
        setup()
        collectViewModels()
        fetchData()
    }

    abstract fun setup()

    /***
     * Request Mandatory Permission
     * Implement Activity level permissions
     ***/
    override fun requestPermissions()
    {
        // Base impl
    }

    override fun showLoading(show: Boolean) {
        // Default impl
    }

    override fun onRefresh() {
        // Default impl
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                this.recreate()
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                this.recreate()
            }
        }
    }
}