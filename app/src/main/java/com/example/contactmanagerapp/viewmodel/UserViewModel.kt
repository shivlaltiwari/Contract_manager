package com.example.contactmanagerapp.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactmanagerapp.room.User
import com.example.contactmanagerapp.room.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel(), Observable {
    val users = repository.users
    private var isUpdateOrDelete = false
    private lateinit var userToUpdateOrDelete: User

    @Bindable
    val inputName = MutableLiveData<String?>()

    @Bindable
    val inputEmail = MutableLiveData<String?>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear all"
    }

    fun saveOrUpdate() {
        if (isUpdateOrDelete) {
            // make update
            userToUpdateOrDelete.name = inputName.value!!
            userToUpdateOrDelete.email = inputEmail.value!!
            update(userToUpdateOrDelete)
        } else {
            // insert func
            val name = inputName.value!!
            val email = inputEmail.value!!
            insert(User(0, name, email))
            inputName.value = null
            inputEmail.value = null
        }


    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(userToUpdateOrDelete)
        } else {
            clearAll()
        }

    }

    fun update(user: User) = viewModelScope.launch {
        repository.update(user)
        // reseting the btn and field
        inputEmail.value = null
        inputName.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value "Save"
        clearAllOrDeleteButtonText.value = "Clear all"

    }

    fun delete(user: User) = viewModelScope.launch {
        repository.delete(user)
        // reseting the btn and field
        inputEmail.value = null
        inputName.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value "Save"
        clearAllOrDeleteButtonText.value = "Clear all"
    }

    fun insert(user: User) = viewModelScope.launch {
        repository.insert(user);
    }

    fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

    fun initUpdateAndDelete(seleteItem: User) {
        inputEmail.value = seleteItem.name
        inputName.value = seleteItem.email
        isUpdateOrDelete = true
        userToUpdateOrDelete = seleteItem
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }


}