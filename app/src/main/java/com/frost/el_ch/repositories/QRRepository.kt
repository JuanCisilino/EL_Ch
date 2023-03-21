package com.frost.el_ch.repositories

import com.frost.el_ch.network.QRService

class QRRepository(private val api: QRService) {

    fun getQR(name: String) = api.getQRCode(name)

}