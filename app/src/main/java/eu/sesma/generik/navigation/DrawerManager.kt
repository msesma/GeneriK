package eu.sesma.generik.navigation

import android.support.v7.widget.Toolbar
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import co.zsmb.materialdrawerkt.builders.drawer
import co.zsmb.materialdrawerkt.builders.footer
import co.zsmb.materialdrawerkt.draweritems.badgeable.primaryItem
import com.mikepenz.materialdrawer.Drawer
import eu.sesma.generik.R
import eu.sesma.generik.repository.LoginRepository
import eu.sesma.generik.ui.BaseActivity
import eu.sesma.generik.usecases.LogoutUseCase
import javax.inject.Inject


class DrawerManager @Inject
constructor(
        private val navigator: Navigator,
        private val logoutUseCase: LogoutUseCase,
        private val activity: BaseActivity,
        private val repository: LoginRepository
) {

    @BindView(R.id.material_drawer_header_name)
    lateinit var name: TextView
    @BindView(R.id.material_drawer_header_email)
    lateinit var email: TextView

    fun configureDrawer(activityToolbar: Toolbar): Drawer {
        val header: ViewGroup = activity.layoutInflater.inflate(
                R.layout.material_drawer_sticky_header, null, false
        ) as ViewGroup
        ButterKnife.bind(this, header)

        val drawer = activity.drawer {
            toolbar = activityToolbar
            translucentStatusBar = false
            actionBarDrawerToggleEnabled = true
            actionBarDrawerToggleAnimated = true
            footerDivider = false
            headerDivider = false
            stickyFooterShadow = false
            stickyHeaderShadow = false
            selectedItem = -1

            stickyHeader = header
            val user = repository.getUser()
            name.setText(user?.name)
            email.setText(user?.email)

            primaryItem(R.string.main_menu) {
                identifier = R.id.main.toLong()
                icon = R.drawable.abc_ic_star_black_36dp
                iconTintingEnabled = true
                selectable = true
                onClick { _ ->
                    navigator.navigateToMain()
                    true
                }
            }

            primaryItem(R.string.profile) {
                identifier = R.id.profile.toLong()
                icon = R.drawable.abc_ic_star_black_36dp
                iconTintingEnabled = true
                selectable = true
                onClick { _ ->
                    navigator.navigateToProfile()
                    selected = true
                    false
                }
            }

            footer {
                primaryItem(R.string.settings) {
                    selectable = false
                    onClick { _ ->
                        navigator.navigateToSettings()
                        false
                    }
                }

                primaryItem(R.string.logout) {
                    selectable = false
                    onClick { _ ->
                        logoutUseCase.execute()
                        navigator.navigateToLoginRegister()
                        false
                    }
                }

                primaryItem(R.string.legal) {
                    selectable = false
                    onClick { _ ->
                        navigator.navigateToTerms()
                        false
                    }
                }
            }

        }
        return drawer
    }
}

