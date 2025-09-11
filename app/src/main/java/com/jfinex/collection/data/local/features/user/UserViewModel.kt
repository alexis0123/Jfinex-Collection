package com.jfinex.collection.data.local.features.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repo: UserRepo
): ViewModel() {
    val user: StateFlow<User?> = repo.getUser()
        .stateIn(viewModelScope, SharingStarted.Lazily, User(name = ""))

    val isNewUser: StateFlow<Boolean> = user.map { it == null }
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun setUserTo(name: String) {
        viewModelScope.launch {
            val number = (1000..9999).random()
            val firstLetter = (('A'..'Z') + ('a'..'Z')).random()
            val code = (number.toString() + firstLetter.toString())
                .toList().shuffled().joinToString("")
            repo.setUser(User(name = "$name-$code"))
        }
    }
}