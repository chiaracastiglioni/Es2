package com.example.es2cosmobile.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.es2cosmobile.data.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginState(
    val username: String = "",
    val password: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false
) {
    val canSubmit get() = username.isNotBlank() && password.isNotBlank()
}

interface LoginActions {
    fun setUsername(username: String)
    fun setPassword(password: String)
    fun setLoading(loading: Boolean)
}

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    val actions = object : LoginActions {
        override fun setUsername(username: String) {
            _state.update { it.copy(username = username, errorMessage = null) }
        }

        override fun setPassword(password: String) {
            _state.update { it.copy(password = password, errorMessage = null) }
        }

        override fun setLoading(loading: Boolean) {
            _state.update { it.copy(isLoading = loading, errorMessage = null) }
        }
    }

    fun login(callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }
            val success = userRepository.checkLogin(_state.value.username, _state.value.password)
            Log.d("Prova", success.toString())
            if (success) {
                _state.update {
                    it.copy(isLoading = false)
                }
                callback(true)
            } else {
                _state.update {
                    it.copy(
                        errorMessage = "Controllare le credenziali inserite.",
                        isLoading = false
                    )
                }
                callback(false)
            }
        }
    }
}
