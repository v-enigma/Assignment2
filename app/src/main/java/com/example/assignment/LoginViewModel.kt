package com.example.assignment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.assignment.Data.LoginRepository
import com.example.assignment.Data.model.Result
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository): ViewModel() {
    private val _authenticationResult = MutableLiveData<Result>()
    val authenticationResult: LiveData<Result> get() = _authenticationResult

    fun authenticate(userId: String, password: String) {
        viewModelScope.launch {
            val result = loginRepository.authenticate(userId, password)
            _authenticationResult.value = result
        }
    }
}

class LoginViewModelFactory(private val repository: LoginRepository) : ViewModelProvider.Factory{

   override fun<T:ViewModel> create(modelClass: Class<T>): T {
       if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
           return LoginViewModel(loginRepository = repository) as T
       }
       throw IllegalArgumentException("Unkonw viewModal Class")
   }
}
