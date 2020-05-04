package id.hardian.passwordmanager


import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import id.hardian.passwordmanager.database.AppDatabase
import id.hardian.passwordmanager.database.PasswordDataDao
import id.hardian.passwordmanager.di.appModule
import id.hardian.passwordmanager.repository.PasswordDataRepository
import id.hardian.passwordmanager.repository.PasswordDataRepositoryImpl
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class AndroidKoinTest : AutoCloseKoinTest() {
    val database: AppDatabase by inject()
    val dao: PasswordDataDao by inject()
    val repo: PasswordDataRepository by inject()

    @Before
    fun before() {
        loadKoinModules(appModule)
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun injectTest() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val tempDb = AppDatabase.getInstance(context)
        val tempRepo = PasswordDataRepositoryImpl(tempDb.passwordDataDao)
        assertEquals(database, tempDb)
        assertEquals(tempDb.passwordDataDao, dao)
        assertEquals(repo.javaClass.simpleName, tempRepo.javaClass.simpleName)
    }
}