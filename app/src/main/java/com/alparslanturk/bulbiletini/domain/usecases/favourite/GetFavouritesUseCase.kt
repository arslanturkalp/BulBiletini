package com.alparslanturk.bulbiletini.domain.usecases.favourite

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.responses.club.clubgetlistwithtickets.ClubGetListWithTicketsResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetFavouritesUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<String, ClubGetListWithTicketsResponse>() {
    override suspend fun getData(params: String?): Result<ClubGetListWithTicketsResponse> = repository.getFavourites(params!!)
}