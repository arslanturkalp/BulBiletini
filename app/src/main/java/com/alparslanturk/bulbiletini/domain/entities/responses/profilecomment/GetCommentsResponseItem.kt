package com.alparslanturk.bulbiletini.domain.entities.responses.profilecomment

import com.alparslanturk.bulbiletini.data.entities.models.Comment

data class GetCommentsResponseItem(
    val profileCommentList: List<Comment>
)
