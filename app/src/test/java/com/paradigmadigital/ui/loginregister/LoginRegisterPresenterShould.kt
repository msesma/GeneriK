package com.paradigmadigital.ui.loginregister

import com.paradigmadigital.navigation.Navigator
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class LoginRegisterPresenterShould {
    @Mock
    private lateinit var navigator: Navigator

    private lateinit var presenter: LoginRegisterPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = LoginRegisterPresenter(navigator)
    }

    @Test
    fun initializeDecoratorWhenInitialized() {

    }
}