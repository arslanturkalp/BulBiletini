package com.alparslanturk.biletdevret.data.entities.models

data class UserMessage(
    val contactList: List<ContactList>,
    val wasNotSeenTotalMessageCount: Int,
    val wasNotSeenTotalContactCount: Int
)
