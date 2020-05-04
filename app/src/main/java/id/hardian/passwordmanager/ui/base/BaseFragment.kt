package id.hardian.passwordmanager.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import timber.log.Timber

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("On Create View")
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        setupUI()
        setupData()
        setupObserver()
        setupListener()

        return binding.root
    }

    abstract fun getLayout(): Int
    abstract fun setupUI()
    abstract fun setupData()
    abstract fun setupObserver()
    abstract fun setupListener()

}