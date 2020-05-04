package id.hardian.passwordmanager.repository

import androidx.lifecycle.LiveData
import id.hardian.passwordmanager.database.PasswordAccount
import id.hardian.passwordmanager.database.PasswordData
import id.hardian.passwordmanager.database.PasswordDataWithAccounts

interface PasswordDataRepository {

    fun getAllPasswordData(): LiveData<List<PasswordData>>

    fun getAllPasswordDataWithName(name: String): List<PasswordData>

    suspend fun getPasswordById(id: Long): PasswordData

    fun getAllPasswordWithAccounts(): LiveData<List<PasswordDataWithAccounts>>

    fun getPasswordWithAccountsById(id: Long): PasswordDataWithAccounts

    suspend fun insertPasswordData(data: PasswordData): Long

    suspend fun updatePasswordData(data: PasswordData)

    suspend fun deletePasswordData(data: PasswordData)
}

interface PasswordAccountRepository {

    fun getAllAccount(): LiveData<List<PasswordAccount>>

    suspend fun getAllAccountByPasswordId(passwordId: Long): List<PasswordAccount>

    suspend fun getAccountById(accountId: Long): PasswordAccount

    suspend fun insertAccount(vararg data: PasswordAccount)

    suspend fun updateAccount(data: PasswordAccount)

    suspend fun deleteAccount(data: PasswordAccount)

    suspend fun deleteAllAccountByPasswordId(passwordId: Long)
}