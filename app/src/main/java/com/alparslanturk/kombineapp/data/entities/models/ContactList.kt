package com.alparslanturk.kombineapp.data.entities.models

data class ContactList(
    val user: TicketUser,
    val wasNotSeenMessageCount: Int,
    val lastMessage: String?
)
