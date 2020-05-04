package id.hardian.passwordmanager

import android.app.Application
import id.hardian.passwordmanager.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initTimber()
        initKoin()

    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
        Timber.d("Timber has been initialize")
    }

    private fun initKoin() {
        startKoin{
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule)
            Timber.d("Koin has been initialize")
        }
    }
}