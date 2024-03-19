package com.alparslanturk.biletdevret.domain.entities.responses.match

import com.alparslanturk.biletdevret.data.entities.models.TicketMatch

data class GetMatchListResponseItem (
    val matchList: List<TicketMatch>
)