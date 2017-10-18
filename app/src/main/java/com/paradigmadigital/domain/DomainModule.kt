package com.paradigmadigital.domain

import android.arch.persistence.room.Room
import android.content.Context
import com.paradigmadigital.domain.db.Database
import com.paradigmadigital.domain.db.UserDao
import com.paradigmadigital.repository.NetworkResultLiveData
import com.paradigmadigital.repository.Preferences
import com.paradigmadigital.repository.Repository
import com.paradigmadigital.repository.SecurePreferences
import com.paradigmadigital.repository.providers.RegisterProvider
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class DomainModule() {

    @Singleton
    @Provides
    fun provideFeedDb(application: Context) =
            Room.databaseBuilder(application, Database::class.java, "data.db")
                    .allowMainThreadQueries()
                    .build()


    @Singleton
    @Provides
    fun provideChannelsDao(db: Database) = db.userDao()

    @Singleton
    @Provides
    fun provideExecutor(): Executor = Executors.newSingleThreadExecutor()

    @Singleton
    @Provides
    fun provideRepository(preferences: Preferences,
                          registerProvider: RegisterProvider,
                          networkResultLiveData: NetworkResultLiveData,
                          userDao: UserDao,
                          securePreferences: SecurePreferences) =
            Repository(preferences, registerProvider, networkResultLiveData, userDao, securePreferences)

}