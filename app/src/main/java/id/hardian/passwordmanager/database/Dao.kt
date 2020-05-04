package id.hardian.passwordmanager.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PasswordDataDao {
    @Query("SELECT * FROM password_data ORDER BY password_name")
    fun getAllPasswordData(): LiveData<List<PasswordData>>

    @Query("SELECT * FROM password_data WHERE password_name like :name ORDER BY password_name")
    fun getAllPasswordDataWithName(name: String): List<PasswordData>

    @Query("SELECT * FROM password_data WHERE id = :id")
    suspend fun getPasswordById(id: Long): PasswordData

    @Query("SELECT * FROM password_data ORDER BY password_name")
    fun getAllPasswordWithAccounts(): LiveData<List<PasswordDataWithAccounts>>

    @Query("SELECT * FROM password_data WHERE id = :id")
    fun getPasswordWithAccountsById(id: Long): PasswordDataWithAccounts

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPasswordData(data: PasswordData): Long

    @Update
    suspend fun updatePasswordData(data: PasswordData)

    @Delete
    suspend fun deletePasswordData(data: PasswordData)
}

@Dao
interface PasswordAccountDao {
    @Query("SELECT * FROM password_account")
    fun getAllAccount(): LiveData<List<PasswordAccount>>

    @Query("SELECT * FROM password_account WHERE password_id = :passwordId")
    suspend fun getAllAccountByPasswordId(passwordId: Long): List<PasswordAccount>

    @Query("SELECT * FROM password_account WHERE id = :accountId")
    suspend fun getAccountById(accountId: Long): PasswordAccount

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(vararg data: PasswordAccount)

    @Update
    suspend fun updateAccount(data: PasswordAccount)

    @Delete
    suspend fun deleteAccount(data: PasswordAccount)

    @Query("DELETE FROM password_account WHERE password_id = :passwordId")
    suspend fun deleteAllAccountByPasswordId(passwordId: Long)
}