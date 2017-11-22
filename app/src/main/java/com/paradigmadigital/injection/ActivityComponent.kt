package com.paradigmadigital.injection

import com.paradigmadigital.platform.ActivityModule
import com.paradigmadigital.ui.BaseActivity
import com.paradigmadigital.ui.changepass.ChangePassActivity
import com.paradigmadigital.ui.detail.DetailActivity
import com.paradigmadigital.ui.inputcode.InputCodeActivity
import com.paradigmadigital.ui.login.LoginActivity
import com.paradigmadigital.ui.loginregister.LoginRegisterActivity
import com.paradigmadigital.ui.main.MainActivity
import com.paradigmadigital.ui.profile.ProfileActivity
import com.paradigmadigital.ui.register.RegisterActivity
import com.paradigmadigital.ui.splash.SplashActivity
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(splashActivity: SplashActivity)

    fun inject(loginRegisterActivity: LoginRegisterActivity)

    fun inject(loginActivity: LoginActivity)

    fun inject(registerActivity: RegisterActivity)

    fun inject(mainActivity: MainActivity)

    fun inject(profileActivity: ProfileActivity)

    fun inject(mainActivity: DetailActivity)

    fun inject(inputCodeActivity: InputCodeActivity)

    fun inject(changePassActivity: ChangePassActivity)

    //Exposed to sub-graphs.
    fun activity(): BaseActivity
}
