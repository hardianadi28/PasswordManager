package id.hardian.passwordmanager.ui.passwordadd

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import id.hardian.passwordmanager.R
import id.hardian.passwordmanager.databinding.FragmentPasswordAddBinding
import id.hardian.passwordmanager.ui.base.BaseFragment
import id.hardian.passwordmanager.viewmodel.PasswordAddViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class PasswordAddFragment : BaseFragment<FragmentPasswordAddBinding>() {

    private val vModel: PasswordAddViewModel by viewModel()

    override fun getLayout(): Int = R.layout.fragment_password_add

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("PasswordAddViewModel of PasswordAddFragment ${vModel.toString()}")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun setupObserver() {
        vModel.doneSaving.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(PasswordAddFragmentDirections.actionPasswordAddFragmentToPasswordListFragment())
                vModel.doneSavingFinish()
            }
        })

        vModel.nameValidateFlag.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.nameInputLayout.error = "Password Name is required"
                vModel.nameValidateFinish()
            }
        })

        vModel.urlValidateFlag.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.websiteInputLayout.error = "Website URL is required"
                vModel.urlValidateFinish()
            }
        })

    }

    override fun setupListener() {
        binding.nameEditText.setOnKeyListener { _, _, _ ->
            binding.nameInputLayout.error = null
            false
        }

        binding.websiteEditText.setOnKeyListener { _, _, _ ->
            binding.websiteInputLayout.error = null
            false
        }

        binding.passwordSaveButton.setOnClickListener {
            vModel.onClickSave(
                binding.nameEditText.text.toString(),
                binding.websiteEditText.text.toString(),
                binding.accountEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        /*binding.addAccountButton.setOnClickListener {
            val dialogFragment = AccountAddFragment()
            val bundle = Bundle()
            bundle.putBoolean("notAlertDialog", true)
            dialogFragment.arguments = bundle
            val ft = activity?.supportFragmentManager?.beginTransaction()
            val prev = activity?.supportFragmentManager?.findFragmentByTag("dialog")
            if (prev != null) {
                ft?.remove(prev)
            }
            ft?.addToBackStack(null)
            if (ft != null) {
                dialogFragment.show(ft, "dialog")
            }
        }*/
    }

    override fun setupData() {
        vModel.loadData()
    }

    override fun setupUI() {
        binding.viewModel = vModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

}
