package id.hardian.passwordmanager

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import id.hardian.passwordmanager.database.*
import id.hardian.passwordmanager.repository.PasswordAccountRepository
import id.hardian.passwordmanager.repository.PasswordAccountRepositoryImpl
import id.hardian.passwordmanager.repository.PasswordDataRepository
import id.hardian.passwordmanager.repository.PasswordDataRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var db: AppDatabase
    private lateinit var disposable: CompositeDisposable
    private lateinit var repo: PasswordDataRepository
    private lateinit var accountRepo: PasswordAccountRepository

    val password = PasswordData(passwordName = "test1", passwordUrl = "url1")
    val account1 = PasswordAccount(
        accountUsername = "test1Account1",
        accountPassword = "test1Account1",
        passwordId = password.id
    )
    val account2 = PasswordAccount(
        accountUsername = "test1Account2",
        accountPassword = "test1Account2",
        passwordId = password.id
    )

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun prepareDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        disposable = CompositeDisposable()
        repo = PasswordDataRepositoryImpl(db.passwordDataDao)
        accountRepo = PasswordAccountRepositoryImpl(db.passwordAccountDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
        disposable.dispose()
    }

    @Test
    @Throws(Exception::class)
    fun insertDataTest() {
        repo.insertPasswordData(password)
        accountRepo.insertAccount(account1, account2)
    }

    private fun insertData() {
        repo.insertPasswordData(password)
        accountRepo.insertAccount(account1, account2)
    }

    @Test
    @Throws(Exception::class)
    fun writePasswordData() {

        insertData()

        val savedPassword = repo.getPasswordById(password.id).blockingGet()
        assertEquals(savedPassword.id, password.id)

    }

    @Test
    @Throws(Exception::class)
    fun updatePassword() {
        insertData()
        var updatedPassword: PasswordData
        val string = "test2"
        val savedPassword = repo.getPasswordById(password.id).blockingGet()
        savedPassword.passwordName = string
        repo.updatePasswordData(savedPassword)
        updatedPassword = repo.getPasswordById(password.id).blockingGet()
        assertEquals(updatedPassword.passwordName, string)
    }

    @Test
    @Throws(Exception::class)
    fun writePasswordDataAndAccount() {

        insertData()

        accountRepo.getAllAccountByPasswordId(password.id)
            .test()
            .assertValue {
                it.size == 2
            }

    }

    @Test
    @Throws(Exception::class)
    fun getPasswordWithAccounts() {
        insertData()
        assertEquals(
            repo.getPasswordWithAccountsById(password.id).blockingGet()
                .accounts.size, 2
        )
    }

    @Test
    @Throws(Exception::class)
    fun getPasswordDataByName() {
        insertData()
        val test = repo.getAllPasswordDataWithName("%%").test()

        test.assertValue {
                it.size == 1
            }

        val test2 = repo.getAllPasswordDataWithName("%est2%").test()

        test2.assertValue {
            it.isEmpty()
        }
    }
}