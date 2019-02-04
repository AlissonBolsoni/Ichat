package br.com.alisson.ichat.injection

import android.content.Context
import android.view.inputmethod.InputMethodManager
import br.com.alisson.ichat.app.ChatApplication
import br.com.alisson.ichat.service.ChatService
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import org.greenrobot.eventbus.EventBus
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ChatModule(private val app: ChatApplication){

    companion object {
        private const val URL = "http://192.168.1.132:8080/"
    }

    @Provides
    fun getChatService():ChatService{
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ChatService::class.java)
    }

    @Provides
    fun getPicasso() = Picasso.Builder(app).build()

    @Provides
    fun getEventBus() = EventBus.builder().build()

    @Provides
    fun getInput() = app.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

}
