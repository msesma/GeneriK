package eu.sesma.generik.injection

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import eu.sesma.generik.api.ApiModule
import eu.sesma.generik.domain.DomainModule
import eu.sesma.generik.domain.db.AuthorDao
import eu.sesma.generik.domain.db.PostDao
import eu.sesma.generik.domain.db.UserDao
import eu.sesma.generik.platform.ApplicationModule
import eu.sesma.generik.repository.LoginRepository
import eu.sesma.generik.ui.viewmodels.ResultViewModel
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
