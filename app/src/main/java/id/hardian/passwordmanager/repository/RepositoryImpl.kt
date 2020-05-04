package id.hardian.passwordmanager.repository

import androidx.lifecycle.LiveData
import id.hardian.passwordmanager.database.*

class PasswordDataRepositoryImpl(private val passwordDataDao: PasswordDataDao) :
    PasswordDataRepository {
    override fun getAllPasswordData(): LiveData<List<PasswordData>> =
        passwordDataDao.getAllPasswordData()

    override fun getAllPasswordDataWithName(name: String): List<PasswordData> =
        passwordDataDao.getAllPasswordDataWithName(name)

    override suspend fun getPasswordById(id: Long): PasswordData =
        passwordDataDao.getPasswordById(id)

    override fun getAllPasswordWithAccounts(): LiveData<List<PasswordDataWithAccounts>> =
        passwordDataDao.getAllPasswordWithAccounts()

    override fun getPasswordWithAccountsById(id: Long): PasswordDataWithAccounts =
        passwordDataDao.getPasswordWithAccountsById(id)

    override suspend fun insertPasswordData(data: PasswordData): Long =
        passwordDataDao.insertPasswordData(data)

    override suspend fun updatePasswordData(data: PasswordData) = passwordDataDao.updatePasswordData(data)

    override suspend fun deletePasswordData(data: PasswordData) = passwordDataDao.deletePasswordData(data)

}

class PasswordAccountRepositoryImpl(private val passwordAccountDao: PasswordAccountDao) :
    PasswordAccountRepository {
    override fun getAllAccount(): LiveData<List<PasswordAccount>> =
        passwordAccountDao.getAllAccount()

    override suspend fun getAllAccountByPasswordId(passwordId: Long): List<PasswordAccount> =
        passwordAccountDao.getAllAccountByPasswordId(passwordId)

    override suspend fun getAccountById(accountId: Long): PasswordAccount =
        passwordAccountDao.getAccountById(accountId)

    override suspend fun insertAccount(vararg data: PasswordAccount) =
        passwordAccountDao.insertAccount(*data)

    override suspend fun updateAccount(data: PasswordAccount) = passwordAccountDao.updateAccount(data)

    override suspend fun deleteAccount(data: PasswordAccount) = passwordAccountDao.deleteAccount(data)

    override suspend fun deleteAllAccountByPasswordId(passwordId: Long) =
        passwordAccountDao.deleteAllAccountByPasswordId(passwordId)

}