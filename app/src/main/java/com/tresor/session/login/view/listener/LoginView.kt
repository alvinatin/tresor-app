package com.tresor.session.login.view.listener

import com.tresor.base.BaseView

/**
 * @author by alvinatin on 16/06/18.
 */

interface LoginView : BaseView {
    fun showLoading()

    fun hideLoading()

    fun onCallLogin(email: String, password: String)

    fun onSuccessLogin()

    fun onErrorLogin()

    fun showError()
}