package com.alparslanturk.kombineapp.domain.usecases

import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.responses.club.clubgetlistwithtickets.ClubGetListWithTicketsResponse
import com.alparslanturk.kombineapp.domain.repository.Repository
import com.alparslanturk.kombineapp.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetFavouritesUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<String, ClubGetListWithTicketsResponse>() {
    override suspend fun getData(params: String?): Result<ClubGetListWithTicketsResponse> = repository.getFavourites(params!!)
}