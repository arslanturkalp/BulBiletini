package com.alparslanturk.kombineapp.domain.entities.responses.match

import com.alparslanturk.kombineapp.data.entities.models.TicketMatch

data class GetMatchListResponseItem (
    val matchList: List<TicketMatch>
)