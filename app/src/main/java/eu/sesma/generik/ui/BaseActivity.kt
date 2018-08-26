package eu.sesma.generik.ui

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import eu.sesma.generik.injection.ActivityComponent
import eu.sesma.generik.injection.ApplicationComponent
import eu.sesma.generik.injection.DaggerActivityComponent
import eu.sesma.generik.platform.ActivityModule
import eu.sesma.generik.platform.AndroidApplication
import eu.sesma.generik.ui.viewmodels.ResultViewModel


abstract class BaseActivity : AppCompatActivity() {

    lateinit var resultViewModel: ResultViewModel

    private val applicationComponent: ApplicationComponent
        get() = (application as AndroidApplication).applicationComponent

    protected lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .activityModule(ActivityModule(this))
                .build()

        resultViewModel = ViewModelProviders.of(this).get(ResultViewModel::class.java)
    }

    fun Activity.getRootView() = (window.decorView as ViewGroup).getChildAt(0) as ViewGroup
}
