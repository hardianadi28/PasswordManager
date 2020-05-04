package id.hardian.passwordmanager.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import timber.log.Timber

abstract class BaseDialogFragment<T: ViewDataBinding> : DialogFragment() {
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
    abstract fun setupObserver()
    abstract fun setupListener()
    abstract fun setupData()
    abstract fun setupUI()
}