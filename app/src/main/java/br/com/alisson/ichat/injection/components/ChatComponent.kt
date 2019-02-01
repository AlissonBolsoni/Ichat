package br.com.alisson.ichat.injection.components

import br.com.alisson.ichat.injection.ChatModule
import br.com.alisson.ichat.ui.activity.MainActivity
import dagger.Component

@Component(modules = [ChatModule::class])
interface ChatComponent {

    fun inject(activity: MainActivity)
}