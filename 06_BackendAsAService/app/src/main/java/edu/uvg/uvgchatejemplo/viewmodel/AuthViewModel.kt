package edu.uvg.uvgchatejemplo.viewmodel

import androidx.compose.runtime.MutableState
import edu.uvg.uvgchatejemplo.data.Result
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuth
import edu.uvg.uvgchatejemplo.Injection
import edu.uvg.uvgchatejemplo.data.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val userRepository: UserRepository

    init {
        userRepository = UserRepository(
            FirebaseAuth.getInstance(),
            Injection.instance()
        )
    }

    private val _authResult = MutableLiveData<Result<Boolean>>()
    val authResult: LiveData<Result<Boolean>> get() = _authResult

    private val _alreadyLoggedIn = mutableStateOf(false)
    var alreadyLoggedIn: MutableState<Boolean> = _alreadyLoggedIn

    fun signUp(email: String, password: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            _authResult.value = userRepository.signUp(email, password, firstName, lastName)
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authResult.value = userRepository.login(email, password)
        }
    }
}
