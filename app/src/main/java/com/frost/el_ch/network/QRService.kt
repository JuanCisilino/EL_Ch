package com.frost.el_ch.network

import com.frost.el_ch.network.responses.QRCodeResponse
import retrofit2.http.POST
import retrofit2.http.Query
import rx.Observable

interface QRService {

    @POST("qr-code")
    fun getQRCode(
        @Query("text") text: String,
        @Query("size") size: Int = 500,
        @Query("color") color: String = "000000",
        @Query("bg_color") bgColor: String = "ffffff"
    ): Observable<QRCodeResponse>
}