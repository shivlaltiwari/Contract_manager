package com.example.contactmanagerapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactmanagerapp.databinding.ActivityMainBinding
import com.example.contactmanagerapp.room.User
import com.example.contactmanagerapp.room.UserDataBase
import com.example.contactmanagerapp.room.UserRepository
import com.example.contactmanagerapp.viewUI.MyRecyclerViewAdapter
import com.example.contactmanagerapp.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() , ViewModelStoreOwner {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val saveBtn: Button = findViewById(R.id.button1)
        val deleteBtn: Button = findViewById(R.id.button2)
        // Room
        val dao = UserDataBase.getDatabaseInstance(application).userDAO
        val repository = UserRepository(dao)
        val factory = UserViewModel(repository)
        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
        binding.userViewModel = userViewModel
        binding.lifecycleOwner = this
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        displayUserList()
    }

    private fun displayUserList() {
        userViewModel.users.observe(this, Observer {
            binding.recyclerView.adapter =
                MyRecyclerViewAdapter(it) { selectItem: User -> listItemClick(selectItem) }
        })
    }

    private fun listItemClick(selectedItem: User) {
        Toast.makeText(this, "Selected name is ${selectedItem.name}", Toast.LENGTH_SHORT).show()
        userViewModel.initUpdateAndDelete(selectedItem)
    }
}
