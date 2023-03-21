package com.frost.el_ch.network.responses

import com.google.gson.annotations.SerializedName

data class QRCodeResponse(
    @SerializedName("qr_code")
    val qrCode: String
    )
