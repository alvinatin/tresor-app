package com.tresor.session.register.data.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author by alvinatin on 10/03/18.
 */

data class RegisterResponse(@SerializedName("Fields") @Expose
                            val data: RegisterDetailResponse)

data class RegisterDetailResponse(@SerializedName("field") @Expose
                                      val field: String,
                                      @SerializedName("message") @Expose
                                      val message: String)


