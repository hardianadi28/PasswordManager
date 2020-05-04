package id.hardian.passwordmanager.ui.passwordlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import id.hardian.passwordmanager.R
import id.hardian.passwordmanager.databinding.ActivityPasswordListBinding

class PasswordListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPasswordListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_password_list)

        initToolBar()
        val navController = findNavController(R.id.main_host_fragment)
        val toolbar = binding.toolbar.mainToolbar
        val drawerLayout = binding.drawerLayout
        NavigationUI.setupWithNavController(toolbar, navController, drawerLayout)

    }

    private fun initToolBar() {
        val toolbar = binding.toolbar.mainToolbar
        setSupportActionBar(toolbar)
    }


}
