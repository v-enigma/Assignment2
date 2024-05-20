package com.example.assignment.ui


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.databinding.UserCardBinding
import com.example.assignment.Data.model.User

class UserAdapter( val onEditClicked:(Int)->Unit): ListAdapter<User, UserAdapter.UserViewHolder>(
    UserFeedDiffItemsCallBack
) {
    class UserViewHolder(val binding: UserCardBinding): RecyclerView.ViewHolder(binding.root) {

            fun bind(item: User){
                Log.d("UsersFragment", "Inside ViewHolder.bind")
                binding.name.text = item.name
                binding.age.text = item.age.toString()
                binding.place.text = item.place

            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        Log.d("UsersFragment", "Inside onCreateViewModel")
        val binding =  UserCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)



    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        Log.d("UsersFragment"," $item $holder")
        holder.binding.edit.setOnClickListener{
            onEditClicked(position)
        }

    }


}

object UserFeedDiffItemsCallBack :DiffUtil.ItemCallback<User>(){
    override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem == newItem

    override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem

}