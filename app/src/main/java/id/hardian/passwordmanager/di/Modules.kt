package id.hardian.passwordmanager.di

import android.content.Context
import id.hardian.passwordmanager.database.AppDatabase
import id.hardian.passwordmanager.database.PasswordAccountDao
import id.hardian.passwordmanager.database.PasswordDataDao
import id.hardian.passwordmanager.repository.PasswordAccountRepository
import id.hardian.passwordmanager.repository.PasswordAccountRepositoryImpl
import id.hardian.passwordmanager.repository.PasswordDataRepository
import id.hardian.passwordmanager.repository.PasswordDataRepositoryImpl
import id.hardian.passwordmanager.viewmodel.AccountAddViewModel
import id.hardian.passwordmanager.viewmodel.PasswordAddViewModel
import id.hardian.passwordmanager.viewmodel.PasswordEditViewModel
import id.hardian.passwordmanager.viewmodel.PasswordListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    fun provideDatabase(context: Context): AppDatabase = AppDatabase.getInstance(context)
    fun providePasswordDataDao(database: AppDatabase) = database.passwordDataDao
    fun providePasswordAccountDao(database: AppDatabase) = database.passwordAccountDao
    fun providePasswordDataRepo(dao: PasswordDataDao): PasswordDataRepository =
        PasswordDataRepositoryImpl(dao)

    fun providePasswordAccRepo(dao: PasswordAccountDao): PasswordAccountRepository =
        PasswordAccountRepositoryImpl(dao)

    fun providePasswordListViewModel(repo: PasswordDataRepository) = PasswordListViewModel(repo)
    fun providePasswordAddViewModel(
        repo: PasswordDataRepository,
        repo2: PasswordAccountRepository
    ) = PasswordAddViewModel(repo, repo2)

    fun providePasswordEditViewModel(
        repo: PasswordDataRepository,
        repo2: PasswordAccountRepository
    ) = PasswordEditViewModel(repo, repo2)

    fun provideAccountAddViewModel(repo: PasswordAccountRepository) = AccountAddViewModel(repo)


    single { provideDatabase(get()) }
    single { providePasswordDataDao(get()) }
    single { providePasswordAccountDao(get()) }
    single { providePasswordDataRepo(get()) }
    single { providePasswordAccRepo(get()) }

    viewModel { providePasswordListViewModel(get()) }
    viewModel { providePasswordAddViewModel(get(), get()) }
    viewModel { providePasswordEditViewModel(get(), get()) }
    viewModel { provideAccountAddViewModel(get()) }
}