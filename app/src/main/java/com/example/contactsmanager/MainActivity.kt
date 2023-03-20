package com.example.contactsmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactsmanager.databinding.ActivityMainBinding
import com.example.contactsmanager.room.User
import com.example.contactsmanager.room.UserDatabase
import com.example.contactsmanager.room.UserRepository
import com.example.contactsmanager.videmodel.UserViewModel
import com.example.contactsmanager.videmodel.UserViewModelFactory
import com.example.contactsmanager.viewUI.MyRecyclerViewAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        // ROOM Database
        val dao = UserDatabase.getInstance(application).userDao
        val repository = UserRepository(dao)
        val factory = UserViewModelFactory(repository)

        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
        binding.userViewModel = userViewModel
        binding.lifecycleOwner = this
        initRecyclerView();

    }

    private fun initRecyclerView() {
        binding.recycler.layoutManager = LinearLayoutManager(this)
        DisplayUsersList()
    }

    private fun DisplayUsersList() {
        userViewModel.users.observe(this, Observer {
            binding.recycler.adapter = MyRecyclerViewAdapter(it, {selectedItem: User -> listItemClidked(selectedItem)})
        })
    }

    private fun listItemClidked(selectedItem: User) {
        Toast.makeText(this, "Selected Name is: ${selectedItem.name}", Toast.LENGTH_LONG).show()
        userViewModel.initUpdateAndDelete(selectedItem)
    }

}