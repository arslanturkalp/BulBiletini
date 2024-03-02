package com.alparslanturk.kombineapp.domain.usecases

import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.requests.ticket.GetMatchTicketsRequest
import com.alparslanturk.kombineapp.domain.entities.responses.ticket.GetTicketsResponse
import com.alparslanturk.kombineapp.domain.repository.Repository
import com.alparslanturk.kombineapp.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetMatchTicketsUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<GetMatchTicketsRequest, GetTicketsResponse>() {
    override suspend fun getData(params: GetMatchTicketsRequest?): Result<GetTicketsResponse> = repository.getMatchTickets(params!!.matchID, params.userID)
}