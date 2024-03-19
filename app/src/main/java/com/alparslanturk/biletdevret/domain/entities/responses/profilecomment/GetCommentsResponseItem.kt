package com.alparslanturk.biletdevret.domain.entities.responses.profilecomment

import com.alparslanturk.biletdevret.data.entities.models.Comment

data class GetCommentsResponseItem(
    val profileCommentList: List<Comment>
)
