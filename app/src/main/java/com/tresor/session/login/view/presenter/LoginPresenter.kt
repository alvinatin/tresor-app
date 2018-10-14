package com.tresor.session.login.view.presenter

import com.tresor.base.BasePresenter
import com.tresor.session.common.api.SessionApi
import com.tresor.session.login.data.LoginMapper
import com.tresor.session.login.view.LoginViewModel
import com.tresor.session.login.view.listener.LoginView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author by alvinatin on 16/06/18.
 */

class LoginPresenter(loginView: LoginView) : BasePresenter<LoginView>(loginView) {
    @Inject
    lateinit var sessionApi: SessionApi

    @Inject
    lateinit var loginMapper: LoginMapper

    private var subscription: Disposable? = null

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    fun callLogin(email: String, password: String) {
        view.showLoading()
        subscription = sessionApi
                .login(getRequestBody(email, password))
                .map(loginMapper)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.showLoading() }
                .subscribe(
                        { model: LoginViewModel -> onSucess(model) },
                        { e -> view.showError() }
                )
    }

    fun onSucess(loginViewModel: LoginViewModel) {
        if (loginViewModel.isSuccess) {
            view.onSuccessLogin()
        } else {
            view.onErrorLogin()
        }
    }

    fun getRequestBody(email: String, password: String): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap.put("email", email)
        hashMap.put("password", password)
        return hashMap
    }
}