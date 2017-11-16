package com.paradigmadigital.platform

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.GoogleApiClient
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

    @Provides
    @PerActivity
    internal fun provideSmsRetrieverClient(context: Context) = SmsRetriever.getClient(context)

    @Provides
    @PerActivity
    internal fun provideLinearLayoutManager(activity: BaseActivity) = LinearLayoutManager(activity as Context)

    @Provides
    @PerActivity
    internal fun funProvideApiClient(activity: BaseActivity): GoogleApiClient {
        return GoogleApiClient.Builder(activity)
                .enableAutoManage(activity,null)
                .addApi(Auth.CREDENTIALS_API)
                .build()
    }
}
