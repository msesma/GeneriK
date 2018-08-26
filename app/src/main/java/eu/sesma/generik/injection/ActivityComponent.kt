package eu.sesma.generik.injection

import eu.sesma.generik.platform.ActivityModule
import eu.sesma.generik.ui.BaseActivity
import eu.sesma.generik.ui.changepass.ChangePassActivity
import eu.sesma.generik.ui.detail.DetailActivity
import eu.sesma.generik.ui.inputcode.InputCodeActivity
import eu.sesma.generik.ui.login.LoginActivity
import eu.sesma.generik.ui.loginregister.LoginRegisterActivity
import eu.sesma.generik.ui.main.MainActivity
import eu.sesma.generik.ui.pin.PinActivity
import eu.sesma.generik.ui.profile.ProfileActivity
import eu.sesma.generik.ui.register.RegisterActivity
import eu.sesma.generik.ui.splash.SplashActivity
import dagger.Component

@PerActivity
@Component(
        dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(splashActivity: SplashActivity)

    fun inject(pinActivity: PinActivity)

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
