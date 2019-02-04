package br.com.alisson.ichat.app

import android.app.Application
import br.com.alisson.ichat.injection.ChatModule
import br.com.alisson.ichat.injection.components.ChatComponent
import br.com.alisson.ichat.injection.components.DaggerChatComponent

class ChatApplication: Application() {

    private lateinit var component: ChatComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerChatComponent.builder()
            .chatModule(ChatModule(this))
            .build()
    }

    fun getComponent(): ChatComponent {

        return component

    }

}
