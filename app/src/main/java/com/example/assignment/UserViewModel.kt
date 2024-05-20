package com.example.assignment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.assignment.Data.UserRepository
import com.example.assignment.Data.model.LoginDetail
import com.example.assignment.Data.model.SORT
import com.example.assignment.Data.model.User
import kotlinx.coroutines.launch


class UserViewModel(private val repository: UserRepository): ViewModel() {
     private  var _users  = MediatorLiveData<List<User>>()
     val users :LiveData<List<User>> =_users
    private val _isUserIdAvailable = MutableLiveData<Boolean>()
    val isUserIdAvailable: LiveData<Boolean> get() = _isUserIdAvailable
     init{
         sortBy(SORT.NAME)
     }
    private fun sortBy(sort: SORT){
        val sortedLiveData = when(sort){
            SORT.NAME -> repository.getUserSortedByName()
            SORT.AGE -> repository.getUserSortedByAge()
            SORT.PLACE -> repository.getUserSortedByPlace()
        }
        _users.addSource(sortedLiveData){users ->
            _users.value = users

        }
    }
    fun sortByPlace() {
        sortBy(SORT.PLACE)
    }

    fun sortByName() {
        sortBy(SORT.NAME)
    }

    fun sortByAge(){
        sortBy(SORT.AGE)
    }

    fun add(user: User, loginDetail: LoginDetail) = viewModelScope.launch {
        repository.add(user,loginDetail)
    }
    fun update(user: User) = viewModelScope.launch {
        repository.update(user)
    }
     fun deleteUser(user: User) =viewModelScope.launch{
        repository.deleteUser(user)
    }
    fun isUserIdAvailable(userId:String) = viewModelScope.launch{
        val available = repository.isUserIdAvailable(userId)
        _isUserIdAvailable.postValue(available)
    }




}

class UserViewModelFactory(private val repository: UserRepository): ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>):T{
        if(modelClass.isAssignableFrom(UserViewModel::class.java)){
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}