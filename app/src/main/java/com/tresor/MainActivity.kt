package com.tresor

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tresor.session.login.LoginActivity
import com.tresor.session.register.view.activity.RegisterActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java)) }

        register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java)) }
    }
}
