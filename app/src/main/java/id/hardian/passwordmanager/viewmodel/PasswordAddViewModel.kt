package id.hardian.passwordmanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.hardian.passwordmanager.database.PasswordAccount
import id.hardian.passwordmanager.database.PasswordData
import id.hardian.passwordmanager.repository.PasswordAccountRepository
import id.hardian.passwordmanager.repository.PasswordDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class PasswordAddViewModel(
    private val repo: PasswordDataRepository,
    private val accountRepo: PasswordAccountRepository
) :
    ViewModel() {
    private var _doneSavingFlag = MutableLiveData<Boolean>()
    val doneSaving: LiveData<Boolean> = _doneSavingFlag

    private var _nameValidateFlag = MutableLiveData<Boolean>()
    val nameValidateFlag: LiveData<Boolean> = _nameValidateFlag

    private var _urlValidateFlag = MutableLiveData<Boolean>()
    val urlValidateFlag: LiveData<Boolean> = _urlValidateFlag

    private var _addAccountFlag = MutableLiveData<Boolean>()
    val addAccountFlag: LiveData<Boolean> = _addAccountFlag


    fun loadData() {
        Timber.d("View Model is Initialized")
        _addAccountFlag.value = false
    }

    public override fun onCleared() {
        super.onCleared()
        Timber.d("PasswordAddViewModel is cleared")
    }

    fun doneSavingFinish() {
        _doneSavingFlag.value = false
    }

    fun nameValidateFinish() {
        _nameValidateFlag.value = false
    }

    fun urlValidateFinish() {
        _urlValidateFlag.value = false
    }

    fun onClickSave(name: String, url: String, account: String, password: String) {
        if (!validateInput(name, url)) return
        viewModelScope.launch {
            val id = insert(name, url)
            insertAccount(account, password, id)
            _doneSavingFlag.value = true
        }
    }

    private suspend fun insert(name: String, url: String): Long {
        val passwordData = PasswordData(0)
        passwordData.passwordName = name
        passwordData.passwordUrl = url
        var id: Long = 0
        withContext(Dispatchers.IO) {
            id = repo.insertPasswordData(passwordData)
        }

        return id
    }

    private suspend fun insertAccount(account: String, password: String, passwordId: Long) {
        Timber.d("Password ID: $passwordId")
        val accountData = PasswordAccount(0, account, password, passwordId)
        withContext(Dispatchers.IO) {
            accountRepo.insertAccount(accountData)
        }
    }

    private fun validateInput(name: String, url: String): Boolean {
        var returnVal = true
        if (name.isBlank()) {
            _nameValidateFlag.value = true
            returnVal = false
        }
        if (url.isBlank()) {
            _urlValidateFlag.value = true
            returnVal = false
        }
        return returnVal
    }

}