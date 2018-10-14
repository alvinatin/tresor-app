package com.tresor.session.register.data

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.tresor.common.network.interceptor.ErrorModel
import com.tresor.common.network.interceptor.ErrorParent
import com.tresor.session.register.data.pojo.RegisterDetailResponse
import com.tresor.session.register.data.pojo.RegisterResponse
import com.tresor.session.register.view.RegisterViewModel
import io.reactivex.functions.Function
import retrofit2.Response
import javax.inject.Inject

/**
 * @author by alvinatin on 22/04/18.
 */

class RegisterMapper @Inject constructor() : Function<Response<Any>, RegisterViewModel> {

    override fun apply(t: Response<Any>): RegisterViewModel {
        return checkIfError(t)
    }

    private fun checkIfError(registerResponse: Response<Any>): RegisterViewModel {
        //TODO Atin betulin ini
        return if (registerResponse.isSuccessful) {
            RegisterViewModel(true,"", "")
        } else {
            if (registerResponse.errorBody() == null)
                RegisterViewModel(false,"", "")
            else {
                val error = Gson().fromJson<Array<ErrorModel>>(registerResponse.errorBody()!!.string(), Array<ErrorModel>::class.java)
                Log.d("tes", error.get(0).message)
                //val body = Gson().fromJson(registerResponse.errorBody().toString(), Array<ErrorModel>::class.java).toList()
                return RegisterViewModel(false, "", "")
            }
        }
    }
}

