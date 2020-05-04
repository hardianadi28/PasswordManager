package id.hardian.passwordmanager.ui.accountadd

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.hardian.passwordmanager.R
import id.hardian.passwordmanager.databinding.FragmentAccountAddBinding
import id.hardian.passwordmanager.ui.base.BaseDialogFragment
import id.hardian.passwordmanager.viewmodel.PasswordAddViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class AccountAddFragment : BaseDialogFragment<FragmentAccountAddBinding>() {

    private val vModel: PasswordAddViewModel by viewModel()
    private val passwordId: Long = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        this.isCancelable = false
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("PasswordAddViewModel of PasswordAddFragment ${vModel.toString()}")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun getLayout(): Int = R.layout.fragment_account_add

    override fun setupObserver() {

    }

    override fun setupListener() {
        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.saveButton.setOnClickListener {

        }
    }

    override fun setupData() {

    }

    override fun setupUI() {

    }
}
