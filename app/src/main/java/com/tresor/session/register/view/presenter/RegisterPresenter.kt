package com.tresor.session.register.view.presenter

import com.tresor.TresorApp
import com.tresor.base.BasePresenter
import com.tresor.common.network.interceptor.UnprocessableEntityError
import com.tresor.session.common.api.SessionApi
import com.tresor.session.common.api.SessionRequestBody
import com.tresor.session.common.di.SessionComponent
import com.tresor.session.common.di.SessionModule
import com.tresor.session.register.data.RegisterMapper
import com.tresor.session.register.view.RegisterViewModel
import com.tresor.session.register.view.listener.RegisterView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author by alvinatin on 21/04/18.
 */

class RegisterPresenter(registerView: RegisterView) : BasePresenter<RegisterView>(registerView) {

    @Inject
    lateinit var sessionApi: SessionApi

    @Inject
    lateinit var registerMapper: RegisterMapper

    private var subscription: Disposable? = null

    override fun onViewCreated() {
        super.onViewCreated()
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    fun callRegister(email: String, password: String) {
        view.showLoading()
        subscription = sessionApi
                .register(getRequestBody(email, password))
                .map(registerMapper)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.showLoading() }
                .subscribe(
                        { model: RegisterViewModel -> onSuccess(model) },
                        { e -> onError(e) }
                )
    }

    fun onSuccess(registerViewModel: RegisterViewModel) {
        if (registerViewModel.isRegistered) {
            view.onSuccesRegister(registerViewModel)
        } else {
            view.onErrorRegister(registerViewModel)
        }
    }

    fun onError(e : Throwable){
        when (e) {
            is UnprocessableEntityError -> e.errorBody.iterator().forEach { error ->
                when (error.field) {
                    "email" -> view.showEmailError(error.message)
                    "password" -> view.showPasswordError(error.message)
                }
            }
        }
    }

    fun getRequestBody(email: String, password: String) : HashMap<String, Any> {
        val hashMap : HashMap<String, Any> = HashMap()
        hashMap.put("email", email)
        hashMap.put("password", password)
        return hashMap
    }
}