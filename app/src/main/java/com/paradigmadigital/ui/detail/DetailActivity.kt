package com.paradigmadigital.ui.detail

import android.os.Bundle
import com.paradigmadigital.R
import com.paradigmadigital.domain.entities.PostUiModel
import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.ui.LoggedInBaseActivity
import javax.inject.Inject


class DetailActivity : LoggedInBaseActivity() {

    @Inject
    lateinit var decorator: DetailDecorator
    @Inject
    lateinit var presenter: DetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        activityComponent.inject(this)

        decorator.bind(getRootView())
        presenter.initialize(decorator, intent?.getSerializableExtra(Navigator.EXTRA_ITEM) as PostUiModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
    }
}
