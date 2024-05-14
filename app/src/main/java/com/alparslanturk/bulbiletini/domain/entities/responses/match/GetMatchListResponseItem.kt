package com.alparslanturk.bulbiletini.domain.entities.responses.match

import com.alparslanturk.bulbiletini.data.entities.models.TicketMatch

data class GetMatchListResponseItem (
    val matchList: List<TicketMatch>
)