package com.paradigmadigital.ui.profile

import com.paradigmadigital.domain.entities.User
import com.paradigmadigital.repository.ApiResult

interface ProfileUserInterface {

    fun initialize(delegate: Delegate, user: User)

    fun onResult(result: ApiResult)

    fun setPhone(phone: String)

    interface Delegate {

        fun onProfileEdit(name: String, tel: String, email: String)

        fun onProfileEdited()

    }
}