package com.paradigmadigital.navigation

import android.support.v7.widget.Toolbar
import co.zsmb.materialdrawerkt.builders.accountHeader
import co.zsmb.materialdrawerkt.builders.drawer
import co.zsmb.materialdrawerkt.builders.footer
import co.zsmb.materialdrawerkt.draweritems.badgeable.primaryItem
import com.mikepenz.materialdrawer.Drawer
import com.paradigmadigital.R
import com.paradigmadigital.ui.BaseActivity
import com.paradigmadigital.usecases.LogoutUseCase
import javax.inject.Inject


class DrawerManager @Inject
constructor(
        private val navigator: Navigator,
        private val logoutUseCase: LogoutUseCase,
        private val activity: BaseActivity
) {
    fun configureDrawer(activityToolbar: Toolbar): Drawer {
        return activity.drawer {
            toolbar = activityToolbar
            translucentStatusBar = false
            actionBarDrawerToggleEnabled = true
            actionBarDrawerToggleAnimated = true
            footerDivider = false
            headerDivider = false
            stickyFooterShadow = false
            stickyHeaderShadow = false
            selectedItem = -1

            accountHeader {
                background = R.drawable.logo_drawer
            }

            primaryItem(R.string.main_menu) {
                identifier = R.id.main.toLong()
            }

            primaryItem(R.string.profile) {

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
    }
}