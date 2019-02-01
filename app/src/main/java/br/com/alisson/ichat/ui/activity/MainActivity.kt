package br.com.alisson.ichat.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.alisson.ichat.R
import br.com.alisson.ichat.data.entity.ObMessage
import br.com.alisson.ichat.injection.components.ChatComponent
import br.com.alisson.ichat.preferences.Preferences
import br.com.alisson.ichat.service.ChatService
import br.com.alisson.ichat.ui.adapter.MessageAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MessageAdapter

    lateinit var component: ChatComponent

    @Inject
    lateinit var chatService: ChatService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component.inject(this)

        val sp = Preferences(this)
        sp.setClientId(1)

        setContentView(R.layout.activity_main)

        adapter = MessageAdapter(this, ArrayList())
        main_list.adapter = adapter

//        chatService = ChatModule.getChatService()


        listenMessage()

        main_send.setOnClickListener {
            chatService.send(ObMessage(sp.getClientId(), main_message.text.toString()))
                .enqueue(object: Callback<Void>{
                    override fun onFailure(call: Call<Void>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    }
                })
        }
    }

    private fun listenMessage() {
        val call = chatService.listen()
        call.enqueue(object: Callback<ObMessage>{
            override fun onFailure(call: Call<ObMessage>, t: Throwable) {
                listenMessage()
            }

            override fun onResponse(call: Call<ObMessage>, response: Response<ObMessage>) {
                if(response.isSuccessful){
                    val obMessage = response.body()
                    if (obMessage != null){
                        val obMessages = adapter.getMessages()
                        obMessages.add(obMessage)
                        adapter = MessageAdapter(this@MainActivity, obMessages)
                        main_list.adapter = adapter
                        listenMessage()
                    }
                }
            }
        })
    }
}
