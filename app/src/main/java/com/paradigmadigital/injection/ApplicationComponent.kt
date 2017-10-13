package com.paradigmadigital.injection

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import com.paradigmadigital.platform.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class
))
interface ApplicationComponent {

    //Exposed to sub-graphs
    fun provideContext(): Context

    fun provideSharedPreferences(): SharedPreferences

    fun providePackageManager(): PackageManager

}
