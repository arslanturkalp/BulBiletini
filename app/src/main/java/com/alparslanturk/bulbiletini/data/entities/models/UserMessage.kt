package com.alparslanturk.bulbiletini.data.entities.models

data class UserMessage(
    val contactList: List<ContactList>,
    val wasNotSeenTotalMessageCount: Int,
    val wasNotSeenTotalContactCount: Int
)
