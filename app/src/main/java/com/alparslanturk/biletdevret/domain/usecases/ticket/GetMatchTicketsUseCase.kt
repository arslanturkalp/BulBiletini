package com.alparslanturk.biletdevret.domain.usecases.ticket

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.requests.ticket.GetMatchTicketsRequest
import com.alparslanturk.biletdevret.domain.entities.responses.ticket.GetTicketsResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetMatchTicketsUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<GetMatchTicketsRequest, GetTicketsResponse>() {
    override suspend fun getData(params: GetMatchTicketsRequest?): Result<GetTicketsResponse> = repository.getMatchTickets(params!!.matchID, params.userID)
}