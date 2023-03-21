package com.frost.el_ch.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frost.el_ch.model.User
import com.frost.el_ch.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
):ViewModel() {

    var updatedLiveData = MutableLiveData<Unit?>()

    var user: User? = null
    private set

    fun onCreate(mail: String){
        viewModelScope.launch {
            val result = userRepository.getAllUsers()
            user = result.find { it.username == mail }
        }
    }

    fun updateCard(card: String){
        if (card.isEmpty()) updatedLiveData.postValue(null)
        handleCardList(card)
        viewModelScope.launch {
            userRepository.insertUser(user!!)
            updatedLiveData.postValue(Unit)
        }
    }

    private fun handleCardList(card: String){
        val currentCard = user?.cards
            ?.let {
                if (it.contains(";")) {
                    it.split(";") as ArrayList<String>
                } else {
                    arrayListOf(it)
                }
            }
            ?:run { arrayListOf<String>() }

        setList(currentCard, card)
    }

    private fun setList(list: ArrayList<String>, card: String){
        if (list.isEmpty()) {
            user?.cards = card
        } else {
            list.add(card)
            user?.cards = list.joinToString(separator = ";")
        }
    }

}