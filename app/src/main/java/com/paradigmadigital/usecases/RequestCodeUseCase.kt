package com.paradigmadigital.usecases

import com.paradigmadigital.repository.Repository
import javax.inject.Inject

class RequestCodeUseCase
@Inject constructor(
        private val repository: Repository
) {

    fun execute(id: Int) {
        repository.requestCode(id)
    }
}