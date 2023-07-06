package com.example.contactmanagerapp.viewUI

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.contactmanagerapp.R
import com.example.contactmanagerapp.databinding.CardItemBinding
import com.example.contactmanagerapp.room.User

class MyRecyclerViewAdapter(
    private val userList: List<User>,
    private val clickListener: (User) -> Unit
) : RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        val binding: CardItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.card_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(userList[position], clickListener)
    }

    class MyViewHolder(val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, clickListener: (User) -> Unit) {
            binding.nameTextview.text = user.name
            binding.emailTextview.text = user.email
            binding.listItemLayout.setOnClickListener() {
                clickListener(user)
            }
        }

    }
}