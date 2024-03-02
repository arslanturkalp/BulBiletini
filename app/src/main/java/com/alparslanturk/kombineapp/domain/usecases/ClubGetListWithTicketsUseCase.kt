package com.alparslanturk.kombineapp.domain.usecases

import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.requests.club.ClubGetListWithTicketsRequest
import com.alparslanturk.kombineapp.domain.entities.responses.club.clubgetlistwithtickets.ClubGetListWithTicketsResponse
import com.alparslanturk.kombineapp.domain.repository.Repository
import com.alparslanturk.kombineapp.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class ClubGetListWithTicketsUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<ClubGetListWithTicketsRequest, ClubGetListWithTicketsResponse>() {
    override suspend fun getData(params: ClubGetListWithTicketsRequest?): Result<ClubGetListWithTicketsResponse> = repository.clubGetListWithTickets(params?.userID!!, params.filterType!!)
}