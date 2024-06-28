package com.dofi.tb1.view.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.dofi.tb1.data.model.UserLogin
import com.dofi.tb1.data.repository.UserApiRepository

class UserApiViewModel(
    private val repository: UserApiRepository
) : ViewModel() {

    private val _getUser = MutableLiveData<UserLogin>()
    private val _createUser = MutableLiveData<UserLogin>()

    private val _loadingState = MutableLiveData<MutableMap<FetchType, Boolean>>()
    val loadingState: LiveData<MutableMap<FetchType, Boolean>> = _loadingState

    val getUserResponse: LiveData<UserLogin?> = _getUser.switchMap { userData ->
        liveData {
            setLoadingState(FetchType.GET_USER, true)
            repository.getUser(userData.emailOrPhone.orEmpty()).collect {
                if (it.isSuccessful) {
                    emit(it.body())
                } else {
                    emit(null)
                }
                setLoadingState(FetchType.GET_USER, false)
            }
        }
    }

    val createUserResponse: LiveData<UserLogin> = _createUser.switchMap { userData ->
        liveData {
            setLoadingState(FetchType.CREATE_USER, true)
            repository.createUser(userData).collect {
                it.body()?.let { it1 ->
                    emit(it1)
                    setLoadingState(FetchType.CREATE_USER, false)
                }
            }
        }
    }

    fun getUser(userData: UserLogin) {
        _getUser.value = userData
    }

    fun createUser(userData: UserLogin) {
        _createUser.value = userData
    }

    private fun setLoadingState(fetchType: FetchType, isLoading: Boolean) {
        val map = _loadingState.value ?: mutableMapOf()
        map[fetchType] = isLoading
        _loadingState.value = map
    }

}