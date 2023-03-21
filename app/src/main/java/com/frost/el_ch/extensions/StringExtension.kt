package com.frost.el_ch.extensions

import com.frost.el_ch.model.Card

fun String.turnToCard(): Card {
    val cardString = this.split(",")
    return Card(
        number = cardString[0],
        expiration = cardString[1]
    )
}