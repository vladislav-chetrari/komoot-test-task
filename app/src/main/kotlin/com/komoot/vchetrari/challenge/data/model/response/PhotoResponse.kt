package com.komoot.vchetrari.challenge.data.model.response

import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    val id: String,
    val secret: String,
    @SerializedName("server")
    val serverId: String
)