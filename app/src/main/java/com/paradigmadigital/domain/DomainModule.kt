package com.paradigmadigital.domain

import android.arch.persistence.room.Room
import android.content.Context
import com.paradigmadigital.domain.db.Database
import com.paradigmadigital.domain.db.UserDao
import com.paradigmadigital.repository.Preferences
import com.paradigmadigital.repository.Repository
import com.paradigmadigital.repository.SecurePreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule() {

    @Singleton
    @Provides
    fun provideFeedDb(application: Context): Database {
        return Room.databaseBuilder(application, Database::class.java, "data.db")
                .allowMainThreadQueries()
                .build()
    }

    @Singleton
    @Provides
    fun provideChannelsDao(db: Database): UserDao {
        return db.userDao()
    }

    @Singleton
    @Provides
    fun provideRepository(preferences: Preferences,
                          securePreferences: SecurePreferences,
                          userDao: UserDao): Repository {
        return Repository(preferences, securePreferences, userDao)
    }
}