package id.hardian.passwordmanager

import id.hardian.passwordmanager.database.AppDatabase
import id.hardian.passwordmanager.di.appModule
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.junit.Assert.*
import org.koin.dsl.koinApplication
import org.koin.test.AutoCloseKoinTest
import org.koin.test.check.checkModules
import org.mockito.Mockito.mock

class AppKoinTest : AutoCloseKoinTest() {
    val database : AppDatabase by inject()
    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(appModule)
    }

    val mockedAndroidContext = mock(MainApplication::class.java)

    @Test
    fun injectTest() {
        assertEquals(4, 2 + 2)
    }
}