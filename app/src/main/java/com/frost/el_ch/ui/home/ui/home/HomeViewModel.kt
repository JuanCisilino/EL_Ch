package com.frost.el_ch.ui.home.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frost.el_ch.model.User
import com.frost.el_ch.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    var userLiveData = MutableLiveData<User?>()

    var user: User? = null
    private set

    fun onCreate(mail: String){
        viewModelScope.launch {
            val result = repository.getAllUsers()
            user = result.find { it.username == mail }
            userLiveData.postValue(user)
        }
    }
}