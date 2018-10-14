package com.tresor.common.network.interceptor

import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author by alvinatin on 02/06/18.
 */
class NetworkInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        request.newBuilder().addHeader("userId", "101010")

        val response = chain.proceed(request)

        return when(response.code()){
            422 -> {
                val error = Gson().fromJson<Array<ErrorModel>>(response.body()!!.string(), Array<ErrorModel>::class.java)
                Log.d("tes", error.get(0).message)
                throw UnprocessableEntityError(error)
            }
            else -> response
        }
    }
}

fun abcd() {
    Observable.just("")
            .doOnError {
                when (it) {
                    is UnprocessableEntityError -> it.errorBody
                }
            }
}

class UnprocessableEntityError(val errorBody: Array<ErrorModel>): RuntimeException()

class ErrorParent(val fields: Array<ErrorModel>)

class ErrorModel(@SerializedName("field") val field : String,
                 @SerializedName("message") val message : String)