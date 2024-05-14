package com.alparslanturk.bulbiletini.data.entities.models

data class ContactList(
    val user: TicketUser,
    val wasNotSeenMessageCount: Int,
    val lastMessage: String?
)
