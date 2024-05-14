package com.alparslanturk.bulbiletini.domain.usecases.club

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.club.ClubGetListWithTicketsRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.club.clubgetlistwithtickets.ClubGetListWithTicketsResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class ClubGetListWithTicketsUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<ClubGetListWithTicketsRequest, ClubGetListWithTicketsResponse>() {
    override suspend fun getData(params: ClubGetListWithTicketsRequest?): Result<ClubGetListWithTicketsResponse> = repository.clubGetListWithTickets(params?.userID!!, params.filterType!!)
}