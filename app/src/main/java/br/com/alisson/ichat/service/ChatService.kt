package br.com.alisson.ichat.service

import br.com.alisson.ichat.data.entity.ObMessage
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ChatService {

    @POST("polling")
    fun send(@Body obMessage: ObMessage): Call<Void>

    @GET("polling")
    fun listen(): Call<ObMessage>

}
