package br.com.alisson.ichat.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import br.com.alisson.ichat.R
import br.com.alisson.ichat.data.entity.ObMessage
import br.com.alisson.ichat.preferences.Preferences
import com.squareup.picasso.Picasso
import javax.inject.Inject

class MessageAdapter(private val context: Context, private val obMessages: ArrayList<ObMessage>, private val picasso: Picasso): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val message = getItem(position)

        val view = LayoutInflater.from(context).inflate(R.layout.message_item, parent, false)
        val messageText = view.findViewById<TextView>(R.id.item_message_message)
        val avatar = view.findViewById<ImageView>(R.id.item_message_image)

        picasso.load("https://api.adorable.io/avatars/285/${message.id}.png").into(avatar)

        messageText.text = message.message

        val sp = Preferences(context)

        if (sp.getClientId() != message.id){
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
        }else{
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
        }

        return view
    }

    override fun getItem(position: Int) = obMessages[position]

    override fun getItemId(position: Int) = getItem(position).id

    override fun getCount() = obMessages.size

    fun getMessages() = this.obMessages
}