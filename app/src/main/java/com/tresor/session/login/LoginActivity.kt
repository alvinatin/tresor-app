package com.tresor.session.login

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import com.tresor.R
import com.tresor.R.id.*
import com.tresor.TresorApp
import com.tresor.base.BaseActivity
import com.tresor.home.activity.AddPaymentActivity
import com.tresor.session.common.di.DaggerSessionComponent
import com.tresor.session.common.di.SessionComponent
import com.tresor.session.common.di.SessionModule
import com.tresor.session.login.view.listener.LoginView
import com.tresor.session.login.view.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

/**
 * @author by alvinatin on 07/03/18.
 */

class LoginActivity : BaseActivity<LoginPresenter>(), LoginView{

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_login)

        button_login.setOnClickListener {
            v -> presenter.callLogin(et_name.text.toString(), et_password.text.toString())
        }

        initInjector().inject(presenter)
    }

    fun initInjector() : SessionComponent {
        return DaggerSessionComponent.builder()
                .baseAppComponent((application as TresorApp).getBaseAppComponent)
                .sessionModule(SessionModule())
                .build()
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCallLogin(email: String, password: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccessLogin() {
        startActivity(Intent(this, AddPaymentActivity::class.java))
    }

    override fun onErrorLogin() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun instantiatePresenter(): LoginPresenter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}