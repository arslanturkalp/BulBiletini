package com.alparslanturk.kombineapp.domain.entities.responses.profilecomment

import com.alparslanturk.kombineapp.data.entities.models.Comment

data class GetCommentsResponseItem(
    val profileCommentList: List<Comment>
)
