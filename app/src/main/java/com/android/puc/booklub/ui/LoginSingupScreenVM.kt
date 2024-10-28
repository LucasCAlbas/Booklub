package com.android.puc.booklub.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginSignupViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun loginOrSignup(email: String, password: String) {
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _loginState.value = LoginState.SuccessLogin
                    } else {
                        // If sign-in fails, try signing up
                        signUp(email, password)
                    }
                }
        }
    }

    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _loginState.value = if (task.isSuccessful) {
                    LoginState.SuccessLogin
                } else {
                    LoginState.Error(task.exception?.message ?: "Signup failed")
                }
            }
    }

}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object SuccessLogin : LoginState()
    object SuccessSignup : LoginState()
    data class Error(val message: String) : LoginState()
}
