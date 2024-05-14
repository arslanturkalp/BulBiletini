package com.alparslanturk.bulbiletini.domain.usecases.ticket

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.ticket.GetMatchTicketsRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.ticket.GetTicketsResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetMatchTicketsUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<GetMatchTicketsRequest, GetTicketsResponse>() {
    override suspend fun getData(params: GetMatchTicketsRequest?): Result<GetTicketsResponse> = repository.getMatchTickets(params!!.matchID, params.userID)
}