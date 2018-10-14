package com.tresor.session.common.api

import com.tresor.session.login.data.pojo.LoginResponse
import com.tresor.session.register.data.pojo.RegisterDetailResponse
import com.tresor.session.register.data.pojo.RegisterResponse
import io.reactivex.Flowable
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

/**
 * @author by alvinatin on 10/03/18.
 */
interface SessionApi {
    @GET("/sessions")
    @Headers("Content-Type: application/json")
    fun login(@Body hashMap: HashMap<String, @JvmSuppressWildcards Any>):
            Flowable<Response<Any>>

    @POST("/users")
    @Headers("Content-Type: application/json")
    fun register(@Body hashMap: HashMap<String, @JvmSuppressWildcards Any>):
            Flowable<Response<Any>>
}