package com.alparslanturk.kombineapp.data.entities.models

data class UserMessage(
    val contactList: List<ContactList>,
    val wasNotSeenTotalMessageCount: Int,
    val wasNotSeenTotalContactCount: Int
)
