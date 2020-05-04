package id.hardian.passwordmanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.hardian.passwordmanager.repository.PasswordAccountRepository

class AccountAddViewModel(private val repo: PasswordAccountRepository) : ViewModel() {
    private var _doneSavingFlag = MutableLiveData<Boolean>()
    val doneSaving: LiveData<Boolean> = _doneSavingFlag

    private var _usernameValidateFlag = MutableLiveData<Boolean>()
    val usernameValidateFlag: LiveData<Boolean> = _usernameValidateFlag

    private var _passwordValidateFlag = MutableLiveData<Boolean>()
    val passwordValidateFlag: LiveData<Boolean> = _passwordValidateFlag

    fun doneSavingFinish() {
        _doneSavingFlag.value = false
    }

    fun usernameValidateFinish() {
        _usernameValidateFlag.value = false
    }

    fun passwordValidateFinish() {
        _passwordValidateFlag.value = false
    }

    private fun validateInput(username: String, password: String): Boolean {
        var returnVal = true
        if (username.isBlank()) {
            _usernameValidateFlag.value = true
            returnVal = false
        }
        if (password.isBlank()) {
            _passwordValidateFlag.value = true
            returnVal = false
        }
        return returnVal
    }
}