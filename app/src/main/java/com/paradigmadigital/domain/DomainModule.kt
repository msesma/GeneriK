package com.paradigmadigital.domain

import android.arch.persistence.room.Room
import android.content.Context
import com.paradigmadigital.account.OauthAccountManager
import com.paradigmadigital.domain.db.Database
import com.paradigmadigital.domain.db.UserDao
import com.paradigmadigital.domain.mappers.LoginMapper
import com.paradigmadigital.repository.NetworkResultLiveData
import com.paradigmadigital.repository.LoginRepository
import com.paradigmadigital.repository.preferences.Preferences
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
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
    fun provideUserDao(db: Database) = db.userDao()

    @Singleton
    @Provides
    fun provideRepository(networkResultLiveData: NetworkResultLiveData,
                          @Named("non-authenticated") retrofit: Retrofit,
                          userDao: UserDao,
                          preferences: Preferences,
                          loginMapper: LoginMapper,
                          accountManager: OauthAccountManager) =
            LoginRepository(
                    networkResultLiveData,
                    userDao,
                    preferences,
                    accountManager,
                    loginMapper,
                    retrofit
            )

}