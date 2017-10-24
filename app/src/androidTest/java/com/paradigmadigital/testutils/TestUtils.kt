package com.paradigmadigital.testutils

import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers
import android.view.View
import org.hamcrest.Matcher


fun setFocus(): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.isDisplayed()
        }

        override fun getDescription(): String {
            return "Focus on view "
        }

        override fun perform(uiController: UiController, view: View) {
            view.requestFocus()
        }
    }
}