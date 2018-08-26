package eu.sesma.generik.ui.detail

import android.os.Bundle
import eu.sesma.generik.R
import eu.sesma.generik.domain.entities.PostUiModel
import eu.sesma.generik.navigation.Navigator
import eu.sesma.generik.ui.LoggedInBaseActivity
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
