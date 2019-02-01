package br.com.alisson.ichat.preferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class Preferences(private val context: Context) {

    companion object {
        private const val PREFERENCES = "br.com.alisson.ichat.preferences.preferences"
        private const val CLIENT_ID_SP = "CLIENT_ID_SP"
    }

    private val sp: SharedPreferences

    init {
        this.sp = context.getSharedPreferences(PREFERENCES, Activity.MODE_PRIVATE)
    }

    fun getClientId(): Long{
        return this.sp.getLong(CLIENT_ID_SP, 0)
    }

    fun setClientId(clientId: Long){
        val edit = this.sp.edit()
        edit.putLong(CLIENT_ID_SP, clientId)
        edit.apply()
    }

}