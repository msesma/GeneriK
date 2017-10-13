package com.paradigmadigital.platform

import com.paradigmadigital.injection.PerActivity
import com.paradigmadigital.ui.AlertDialog
import com.paradigmadigital.ui.BaseActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: BaseActivity) {

    @Provides
    @PerActivity
    internal fun activity(): BaseActivity = this.activity

    @Provides
    @PerActivity
    internal fun provideAlertDialog(activity: BaseActivity) = AlertDialog(activity)
}
