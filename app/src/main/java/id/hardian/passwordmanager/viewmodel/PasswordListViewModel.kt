package id.hardian.passwordmanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.hardian.passwordmanager.database.PasswordData
import id.hardian.passwordmanager.repository.PasswordDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PasswordListViewModel(private val passwordDataRepository: PasswordDataRepository) :
    ViewModel() {

    private var _selectedId = MutableLiveData<Long>()
    val selectedId: LiveData<Long> = _selectedId

    private val _addButtonFlag = MutableLiveData<Boolean>()
    val addButtonFlag: LiveData<Boolean> = _addButtonFlag

    private var _searchData = MutableLiveData<List<PasswordData>>()
    val searchData: LiveData<List<PasswordData>> = _searchData

    private var _navigateToEdit = MutableLiveData<Boolean>()
    val navigateToEdit = _navigateToEdit

    private var _toggleAppBarMenu = MutableLiveData<Boolean>()
    val toggleAppBarMenu = _toggleAppBarMenu

    fun loadData() = searchData("")


    fun searchData(name: String) {
        viewModelScope.launch {
            loadDataFromDb(name)
        }
    }

    private suspend fun loadDataFromDb(name: String) {
        withContext(Dispatchers.IO) {
            _searchData.postValue(passwordDataRepository.getAllPasswordDataWithName("%$name%"))
        }
    }

    fun resetList() = searchData("")

    fun addButtonClick() {
        _addButtonFlag.value = true
    }

    fun addButtonClickFinish() {
        _addButtonFlag.value = false
    }

    fun onClickListData(dataId: Long) {
        _selectedId.value = dataId
        _toggleAppBarMenu.value = true
    }

    fun toggleAppBarMenuFinish() {
        _selectedId.value = -1L
        _toggleAppBarMenu.value = false
    }

    fun navigateToEditFinish() {
        _navigateToEdit.value = false
    }

    fun onClickEditMenu() {
        _navigateToEdit.value = true
    }


}