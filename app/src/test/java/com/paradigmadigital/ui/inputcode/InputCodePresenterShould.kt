package com.paradigmadigital.ui.inputcode

import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.usecases.RequestCodeUseCase
import com.paradigmadigital.usecases.SetPassUseCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class InputCodePresenterShould {
    @Mock
    private lateinit var setPassUseCase: SetPassUseCase
    @Mock
    private lateinit var requestCodeUseCase: RequestCodeUseCase
    @Mock
    private lateinit var navigator: Navigator
    @Mock
    private lateinit var smsManager: SmsManager

    private lateinit var presenter: InputCodePresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = InputCodePresenter(navigator, smsManager, setPassUseCase, requestCodeUseCase)
    }

    @Test
    fun initializeDecoratorWhenInitialized() {

    }
}
