package br.com.alisson.ichat.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import br.com.alisson.ichat.R
import br.com.alisson.ichat.app.ChatApplication
import br.com.alisson.ichat.data.entity.ObMessage
import br.com.alisson.ichat.envents.MessageEvent
import br.com.alisson.ichat.injection.components.ChatComponent
import br.com.alisson.ichat.preferences.Preferences
import br.com.alisson.ichat.service.ChatService
import br.com.alisson.ichat.ui.adapter.MessageAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MessageAdapter

    lateinit var component: ChatComponent

    @Inject
    lateinit var chatService: ChatService

    @Inject
    lateinit var picasso: Picasso

    @Inject
    lateinit var eventBus: EventBus

    @Inject
    lateinit var input: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as ChatApplication
        component = app.getComponent()
        component.inject(this)

        val sp = Preferences(this)
        sp.setClientId(1)

        setContentView(R.layout.activity_main)


        if (savedInstanceState == null)
            adapter = MessageAdapter(this, ArrayList(), picasso)
        else{
            val messages = savedInstanceState.getSerializable("mensagem") as ArrayList<ObMessage>
            adapter = MessageAdapter(this, messages, picasso)
        }

        main_list.adapter = adapter

        listenMessage(MessageEvent(null))

        main_send.setOnClickListener {
            chatService.send(ObMessage(sp.getClientId(), main_message.text.toString()))
                .enqueue(object: Callback<Void>{
                    override fun onFailure(call: Call<Void>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    }
                })

            main_message.text.clear()
            input.hideSoftInputFromWindow(main_message.windowToken,0)
        }

        picasso.load("https://api.adorable.io/avatars/285/${sp.getClientId()}.png").into(main_image)
        eventBus.register(this)
    }

    override fun onStop() {
        super.onStop()
        eventBus.unregister(this)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState!!.putSerializable("mensagem", adapter.getMessages())
    }

    @Subscribe
    fun putOnList(messageEvent: MessageEvent){
        if (messageEvent.message != null){
            val obMessages = adapter.getMessages()
            obMessages.add(messageEvent.message)
            adapter = MessageAdapter(this@MainActivity, obMessages, picasso)
            main_list.adapter = adapter
        }
    }

    @Subscribe
    fun listenMessage(messageEvent: MessageEvent) {
        val call = chatService.listen()
        call.enqueue(object: Callback<ObMessage>{
            override fun onFailure(call: Call<ObMessage>, t: Throwable) {
                eventBus.post(MessageEvent(null))
            }

            override fun onResponse(call: Call<ObMessage>, response: Response<ObMessage>) {
                if(response.isSuccessful){
                    val obMessage = response.body()
                    if (obMessage != null){

                        eventBus.post(MessageEvent(obMessage))
                    }
                }
            }
        })
    }
}
