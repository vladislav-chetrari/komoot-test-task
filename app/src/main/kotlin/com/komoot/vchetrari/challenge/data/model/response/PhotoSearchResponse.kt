package com.komoot.vchetrari.challenge.data.model.response

import com.google.gson.annotations.SerializedName

data class PhotoSearchResponse(
    @SerializedName("stat")
    val status: String,
    val photos: Photos
) {

    data class Photos(
        @SerializedName("photo")
        val value: List<PhotoResponse>
    )
}