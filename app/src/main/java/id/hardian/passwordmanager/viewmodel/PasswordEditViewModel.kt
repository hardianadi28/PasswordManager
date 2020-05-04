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

class PasswordEditViewModel(
    private val repo: PasswordDataRepository,
    private val accountRepo: PasswordAccountRepository
) : ViewModel() {

    private var _dataPasswordId: Long = 0L
    val dataPasswordId: Long = _dataPasswordId

    private var _dataPassword = MutableLiveData<PasswordData>()
    val dataPassword: LiveData<PasswordData> = _dataPassword

    private var _dataAccount = MutableLiveData<List<PasswordAccount>>()
    val dataAccount: LiveData<List<PasswordAccount>> = _dataAccount

    private var _doneSavingFlag = MutableLiveData<Boolean>()
    val doneSaving: LiveData<Boolean> = _doneSavingFlag

    private var _nameValidateFlag = MutableLiveData<Boolean>()
    val nameValidateFlag: LiveData<Boolean> = _nameValidateFlag

    private var _urlValidateFlag = MutableLiveData<Boolean>()
    val urlValidateFlag: LiveData<Boolean> = _urlValidateFlag

    fun doneSavingFinish() {
        _doneSavingFlag.value = false
    }

    fun nameValidateFinish() {
        _nameValidateFlag.value = false
    }

    fun urlValidateFinish() {
        _urlValidateFlag.value = false
    }

    fun loadData(dataId: Long) {
        _dataPasswordId = dataId
        viewModelScope.launch {
            getPasswordData(dataId)
            getAccountData(dataId)
        }
    }

    private suspend fun getPasswordData(dataId: Long) {
        withContext(Dispatchers.IO) {
            _dataPassword.postValue(repo.getPasswordById(dataId))
        }
    }

    private suspend fun getAccountData(dataId: Long) {
        withContext(Dispatchers.IO) {
            _dataAccount.postValue(accountRepo.getAllAccountByPasswordId(dataId))
        }
    }

    fun onClickSave(name: String, url: String) {
        if (!validateInput(name, url)) return

        viewModelScope.launch {
            val preUpdateData = dataPassword.value
            preUpdateData?.let {
                it.passwordName = name
                it.passwordUrl = url

                update(it)
            }
            _doneSavingFlag.value = true
        }
    }

    private suspend fun update(data: PasswordData) = withContext(Dispatchers.IO) {
        repo.updatePasswordData(data)
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