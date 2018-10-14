package com.tresor.session.login.data

import com.tresor.session.login.view.LoginViewModel
import io.reactivex.functions.Function
import retrofit2.Response
import javax.inject.Inject

/**
 * @author by alvinatin on 17/06/18.
 */

class LoginMapper @Inject constructor() : Function<Response<Any>, LoginViewModel> {

    override fun apply(t: Response<Any>): LoginViewModel {
        //TODO atin betulin ini
        return LoginViewModel(true, "", "")
    }
}