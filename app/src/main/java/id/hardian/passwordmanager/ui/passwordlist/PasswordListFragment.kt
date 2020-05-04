package id.hardian.passwordmanager.ui.passwordlist

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import id.hardian.passwordmanager.R
import id.hardian.passwordmanager.databinding.FragmentPasswordListBinding
import id.hardian.passwordmanager.ui.base.BaseFragment
import id.hardian.passwordmanager.viewmodel.PasswordListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class PasswordListFragment : BaseFragment<FragmentPasswordListBinding>() {

    private val vModel: PasswordListViewModel by viewModel()
    private lateinit var adapter: PasswordDataListAdapter
    private lateinit var toolbarMenu: Menu
    private lateinit var searchView: SearchView

    override fun getLayout(): Int = R.layout.fragment_password_list

    override fun setupUI() {
        setupAdapter()
        setupRecyclerView()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = vModel
        setHasOptionsMenu(true)
    }

    private fun setupAdapter() {
        adapter = PasswordDataListAdapter(ListPasswordDataClickListener { dataId ->
            vModel.onClickListData(dataId)
        })
    }

    private fun setupRecyclerView() {
        binding.passwordDataRecyclerView.adapter = adapter
        binding.passwordDataRecyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.passwordDataRecyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun setupObserver() {
        vModel.addButtonFlag.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(PasswordListFragmentDirections.actionPasswordListFragmentToPasswordAddFragment())
                vModel.addButtonClickFinish()
            }
        })

        vModel.searchData.observe(viewLifecycleOwner, Observer {
            it.let {
                adapter.submitList(it)
            }
        })

        vModel.toggleAppBarMenu.observe(viewLifecycleOwner, Observer {
            it?.let {
                toggleAppBarMenuVisibility(it)
            }
        })

        vModel.selectedId.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.selectedId = it
            }
        })

        vModel.navigateToEdit.observe(viewLifecycleOwner, Observer {
            if (it) {
                val id = vModel.selectedId.value
                id?.let { dataId ->
                    findNavController().navigate(
                        PasswordListFragmentDirections.actionPasswordListFragmentToPasswordEditFragment(
                            dataId
                        )
                    )
                }
                vModel.toggleAppBarMenuFinish()
                vModel.navigateToEditFinish()
            }
        })
    }

    override fun setupListener() {
        binding.floatingActionButton.setOnClickListener {
            vModel.addButtonClick()
        }
    }

    override fun setupData() {
        vModel.loadData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_menu, menu)
        toolbarMenu = menu
        setupSearchView(menu)

    }

    private fun setupSearchView(menu: Menu) {
        searchView = menu.findItem(R.id.action_search).actionView as SearchView
        configureSearch(searchView)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_edit -> {
            vModel.onClickEditMenu()
            true
        }
        R.id.action_cancel -> {
            vModel.toggleAppBarMenuFinish()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun configureSearch(searchView: SearchView) {
        searchView.setOnCloseListener {
            vModel.resetList()
            return@setOnCloseListener false
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                vModel.searchData(newText ?: "")
                return true
            }

        })
    }

    private fun toggleAppBarMenuVisibility(flag: Boolean) {
        toolbarMenu?.apply {
            findItem(R.id.action_search)?.isVisible = !flag
            findItem(R.id.action_cancel)?.isVisible = flag
            findItem(R.id.action_delete)?.isVisible = flag
            findItem(R.id.action_edit)?.isVisible = flag
        }
    }

}
