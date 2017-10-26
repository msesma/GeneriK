package com.paradigmadigital.injection

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import com.paradigmadigital.api.ApiModule
import com.paradigmadigital.domain.DomainModule
import com.paradigmadigital.domain.db.UserDao
import com.paradigmadigital.platform.ApplicationModule
import com.paradigmadigital.repository.Repository
import com.paradigmadigital.ui.viewmodels.ResultViewModel
import com.paradigmadigital.ui.viewmodels.UserViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        DomainModule::class,
        ApiModule::class
))
interface ApplicationComponent {

    fun inject(into: ResultViewModel)

    fun inject(into: UserViewModel)

    //Exposed to sub-graphs
    fun provideContext(): Context

    fun provideSharedPreferences(): SharedPreferences

    fun providePackageManager(): PackageManager

    fun provideUserDao(): UserDao

    fun provideRepository(): Repository

}
