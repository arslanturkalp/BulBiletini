package com.alparslanturk.bulbiletini.data.entities.models

data class MyComment(
    val userId: String,
    val commentList: List<Comment>,
    val commentedUser: TicketUser,
)