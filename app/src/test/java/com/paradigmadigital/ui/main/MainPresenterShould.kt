package com.paradigmadigital.ui.main

import com.nhaarman.mockito_kotlin.*
import com.paradigmadigital.ui.viewmodels.UserViewModel
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MainPresenterShould {
    @Mock
    private lateinit var decorator: MainUserInterface
    @Mock
    private lateinit var userViewModel: UserViewModel

    private val delegateCaptor = argumentCaptor<MainUserInterface.Delegate>()
    private lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter()
        doNothing().whenever(decorator).initialize(delegateCaptor.capture(), any())
    }

    @Test
    fun initializeDecoratorWhenInitialized() {

        presenter.initialize(decorator, userViewModel)

        verify(decorator).initialize(any(), any())
    }
}