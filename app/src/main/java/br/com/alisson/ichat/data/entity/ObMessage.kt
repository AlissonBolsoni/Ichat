package br.com.alisson.ichat.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ObMessage(
    var id: Long = 0,
    @SerializedName("text")
    var message: String = ""): Serializable
