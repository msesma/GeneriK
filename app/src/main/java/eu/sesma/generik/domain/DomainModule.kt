package eu.sesma.generik.domain

import android.arch.persistence.room.Room
import android.content.Context
import eu.sesma.generik.account.OauthAccountManager
import eu.sesma.generik.domain.db.Database
import eu.sesma.generik.domain.db.UserDao
import eu.sesma.generik.domain.mappers.UserMapper
import eu.sesma.generik.repository.LoginRepository
import eu.sesma.generik.repository.NetworkResultLiveData
import eu.sesma.generik.repository.preferences.Preferences
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

    @Provides
    fun providePostDao(db: Database) = db.postDao()

    @Provides
    fun provideAuthorDao(db: Database) = db.authorDao()

    @Singleton
    @Provides
    fun provideLoginRepository(networkResultLiveData: NetworkResultLiveData,
                               @Named("non-authenticated") retrofit: Retrofit,
                               userDao: UserDao,
                               preferences: Preferences,
                               userMapper: UserMapper,
                               accountManager: OauthAccountManager) =
            LoginRepository(
                    networkResultLiveData,
                    userDao,
                    preferences,
                    accountManager,
                    userMapper,
                    retrofit
            )

}