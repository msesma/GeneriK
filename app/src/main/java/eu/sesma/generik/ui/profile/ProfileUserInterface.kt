package eu.sesma.generik.ui.profile

import eu.sesma.generik.domain.entities.User
import eu.sesma.generik.repository.ApiResult

interface ProfileUserInterface {

    fun initialize(delegate: Delegate, user: User)

    fun onResult(result: ApiResult)

    fun setPhone(phone: String)

    interface Delegate {

        fun onProfileEdit(name: String, tel: String, email: String)

        fun onProfileEdited()

    }
}