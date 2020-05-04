package id.hardian.passwordmanager.ui.passwordedit

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.hardian.passwordmanager.database.PasswordAccount
import id.hardian.passwordmanager.databinding.ListItemPasswordAccountBinding

class AccountListAdapter :
    ListAdapter<PasswordAccount, AccountListAdapter.ViewHolder>(
        AccountListDiffCallBack()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(
            parent
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: ListItemPasswordAccountBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PasswordAccount) {
            binding.accountTextView.text = item.accountUsername
            binding.passwordTextView.text = item.accountPassword
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemPasswordAccountBinding.inflate(inflater, parent, false)
                return ViewHolder(
                    binding
                )
            }
        }
    }

}

class AccountListDiffCallBack : DiffUtil.ItemCallback<PasswordAccount>() {
    override fun areItemsTheSame(oldItem: PasswordAccount, newItem: PasswordAccount): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PasswordAccount, newItem: PasswordAccount): Boolean =
        oldItem == newItem
}