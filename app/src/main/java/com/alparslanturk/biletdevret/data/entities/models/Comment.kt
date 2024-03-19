package com.alparslanturk.biletdevret.data.entities.models

data class Comment(
    val comment: String,
    val createdDate: String,
    val updatedDate: String,
    val createdUserId: String,
    val ownerUser: TicketUser
)
