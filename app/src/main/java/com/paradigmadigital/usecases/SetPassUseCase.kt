package com.paradigmadigital.usecases

import com.paradigmadigital.repository.Repository
import javax.inject.Inject

class SetPassUseCase
@Inject constructor(
        private val repository: Repository
) {

    fun execute(code: String, id: Int) {
        repository.setPass(code, id)
    }
}