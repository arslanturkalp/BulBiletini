package com.alparslanturk.biletdevret.domain.usecases.club

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.requests.club.ClubGetListWithTicketsRequest
import com.alparslanturk.biletdevret.domain.entities.responses.club.clubgetlistwithtickets.ClubGetListWithTicketsResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class ClubGetListWithTicketsUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<ClubGetListWithTicketsRequest, ClubGetListWithTicketsResponse>() {
    override suspend fun getData(params: ClubGetListWithTicketsRequest?): Result<ClubGetListWithTicketsResponse> = repository.clubGetListWithTickets(params?.userID!!, params.filterType!!)
}