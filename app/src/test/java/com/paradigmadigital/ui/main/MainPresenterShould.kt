package com.paradigmadigital.ui.main

import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MainPresenterShould {
    @Mock private lateinit var decorator: MainUserInterface

    private val delegateCaptor = argumentCaptor<MainUserInterface.Delegate>()
    private lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter()
        doNothing().whenever(decorator).initialize(delegateCaptor.capture())
    }

    @Test
    fun initializeDecoratorWhenInitialized() {

        presenter.initialize(decorator)

        verify(decorator).initialize(any())
    }
}