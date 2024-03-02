package com.alparslanturk.kombineapp.data.entities.enums

enum class ChatMessageType(val value: Int) {
    RECEIVED(0),
    SENT(1);

    companion object {
        fun fromInt(value: Int) = entries.first { it.value == value }
    }
}