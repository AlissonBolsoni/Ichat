package br.com.alisson.ichat.injection

import br.com.alisson.ichat.service.ChatService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ChatModule{

    companion object {
        private const val URL = "http://192.168.15.46:8080/"
    }

    @Provides
    fun getChatService():ChatService{
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ChatService::class.java)
    }

}
