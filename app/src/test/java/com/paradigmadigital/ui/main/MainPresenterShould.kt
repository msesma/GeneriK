package com.paradigmadigital.ui.main

import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations


class MainPresenterShould {

    private lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter()
    }

    @Test
    fun initializeDecoratorWhenInitialized() {

    }
}