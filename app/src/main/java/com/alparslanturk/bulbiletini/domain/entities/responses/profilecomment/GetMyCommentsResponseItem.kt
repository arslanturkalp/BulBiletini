package com.alparslanturk.bulbiletini.domain.entities.responses.profilecomment

import com.alparslanturk.bulbiletini.data.entities.models.MyComment

data class GetMyCommentsResponseItem(
    val profileCommentList: List<MyComment>
)
