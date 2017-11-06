package com.paradigmadigital.domain

import android.arch.persistence.room.Room
import android.content.Context
import com.paradigmadigital.api.mappers.UserMapper
import com.paradigmadigital.domain.db.Database
import com.paradigmadigital.domain.db.UserDao
import com.paradigmadigital.domain.mappers.LoginMapper
import com.paradigmadigital.repository.NetworkResultLiveData
import com.paradigmadigital.repository.Repository
import com.paradigmadigital.repository.preferences.Preferences
import com.paradigmadigital.repository.securepreferences.SecurePreferences
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class DomainModule() {

    @Singleton
    @Provides
    fun provideFeedDb(application: Context) =
            Room.databaseBuilder(application, Database::class.java, "data.db")
                    .allowMainThreadQueries()
                    .build()

    @Provides
    fun provideChannelsDao(db: Database) = db.userDao()

    @Singleton
    @Provides
    fun provideRepository(networkResultLiveData: NetworkResultLiveData,
                          userDao: UserDao,
                          securePreferences: SecurePreferences,
                          retrofit: Retrofit,
                          preferences: Preferences,
                          loginMapper: LoginMapper,
                          userMapper: UserMapper) =
            Repository(
                    networkResultLiveData,
                    userDao,
                    securePreferences,
                    preferences,
                    loginMapper,
                    userMapper,
                    retrofit)

}