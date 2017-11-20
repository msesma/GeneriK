package com.paradigmadigital.injection

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import com.paradigmadigital.api.ApiModule
import com.paradigmadigital.domain.DomainModule
import com.paradigmadigital.domain.db.AuthorDao
import com.paradigmadigital.domain.db.PostDao
import com.paradigmadigital.domain.db.UserDao
import com.paradigmadigital.platform.ApplicationModule
import com.paradigmadigital.repository.LoginRepository
import com.paradigmadigital.ui.viewmodels.ResultViewModel
import com.squareup.picasso.Picasso
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        DomainModule::class,
        ApiModule::class
))
interface ApplicationComponent {

    fun inject(into: ResultViewModel)

    //Exposed to sub-graphs
    fun provideContext(): Context

    fun provideSharedPreferences(): SharedPreferences

    fun providePackageManager(): PackageManager

    fun provideLoginRepository(): LoginRepository

    fun provideUserDao(): UserDao

    fun providePostDao(): PostDao

    fun provideAuthorDao(): AuthorDao

    fun providePicasso(): Picasso

    @Named("authenticated")
    fun ProvideAuthRetrofit(): Retrofit

}
