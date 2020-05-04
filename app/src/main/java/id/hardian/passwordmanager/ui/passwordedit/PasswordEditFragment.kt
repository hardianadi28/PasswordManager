package id.hardian.passwordmanager.ui.passwordedit

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import id.hardian.passwordmanager.R
import id.hardian.passwordmanager.databinding.FragmentPasswordEditBinding
import id.hardian.passwordmanager.ui.accountadd.AccountAddFragment
import id.hardian.passwordmanager.ui.base.BaseFragment
import id.hardian.passwordmanager.viewmodel.PasswordEditViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class PasswordEditFragment : BaseFragment<FragmentPasswordEditBinding>() {
    private val vModel: PasswordEditViewModel by viewModel()
    private lateinit var adapter: AccountListAdapter

    override fun getLayout(): Int = R.layout.fragment_password_edit

    override fun setupUI() {
        adapter = AccountListAdapter()
        setupRecyclerView()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = vModel

        setHasOptionsMenu(true)
    }

    private fun setupRecyclerView() {
        binding.listAccountRecyclerView.adapter = adapter
        binding.listAccountRecyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.listAccountRecyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun setupData() {
        val args = PasswordEditFragmentArgs.fromBundle(requireArguments())
        args?.let {
            Timber.d("Password ID: ${it.passwordId}")
            vModel.loadData(it.passwordId)
        }
    }

    override fun setupObserver() {
        vModel.dataPassword.observe(viewLifecycleOwner, Observer {
            binding.data = it
        })

        vModel.dataAccount.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
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

        vModel.doneSaving.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController()
                    .navigate(
                        PasswordEditFragmentDirections
                            .actionPasswordEditFragmentToPasswordListFragment()
                    )
                vModel.doneSavingFinish()
            }
        })

    }

    override fun setupListener() {
        binding.passwordSaveButton.setOnClickListener {
            vModel.onClickSave(
                binding.nameEditText.text.toString(),
                binding.websiteEditText.text.toString()
            )

        }

        binding.addAccountButton.setOnClickListener {
            val dialogFragment = AccountAddFragment()
            val bundle = Bundle()
            bundle.putLong("passwordId", vModel.dataPasswordId)
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
        }
    }
}
