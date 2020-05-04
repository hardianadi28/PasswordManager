package id.hardian.passwordmanager.ui.passwordlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.hardian.passwordmanager.R
import id.hardian.passwordmanager.database.PasswordData
import id.hardian.passwordmanager.databinding.ListItemPasswordDataBinding

class PasswordDataListAdapter(val clickListener: ListPasswordDataClickListener) :
    ListAdapter<PasswordData, PasswordDataListAdapter.ViewHolder>(PasswordDataListDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener, selectedId)
    }

    var selectedId: Long = -1
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    class ViewHolder private constructor(val binding: ListItemPasswordDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: PasswordData,
            clickListener: ListPasswordDataClickListener,
            selectedId: Long
        ) {
            binding.data = item
            binding.clickListener = clickListener
            binding.nameTextView.text = item.passwordName
            binding.urlTextView.text = item.passwordUrl
            if(selectedId==item.id) {
                itemView.setBackgroundResource(R.drawable.selected_item_list)
            }else{
                itemView.setBackgroundResource(0)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemPasswordDataBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }


}

class PasswordDataListDiffCallBack : DiffUtil.ItemCallback<PasswordData>() {
    override fun areItemsTheSame(oldItem: PasswordData, newItem: PasswordData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PasswordData, newItem: PasswordData): Boolean {
        return oldItem == newItem
    }

}

class ListPasswordDataClickListener(val listener: (passwordDataId: Long) -> Unit) {
    fun onClick(data: PasswordData) = listener(data.id)
}