package com.frost.el_ch.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frost.el_ch.model.User
import com.frost.el_ch.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {

    lateinit var users : List<User>
    var registerLiveData = MutableLiveData<Unit?>()

    fun onCreate() {
        viewModelScope.launch {
            val result = repository.getAllUsers()
            if (result.isNotEmpty()) users = result
        }
    }

    fun registerUser(user: User){
        viewModelScope.launch {
            repository.insertUser(user)
            registerLiveData.postValue(Unit)
        }
    }
}