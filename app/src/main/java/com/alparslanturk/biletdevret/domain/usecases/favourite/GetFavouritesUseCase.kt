package com.alparslanturk.biletdevret.domain.usecases.favourite

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.responses.club.clubgetlistwithtickets.ClubGetListWithTicketsResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetFavouritesUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<String, ClubGetListWithTicketsResponse>() {
    override suspend fun getData(params: String?): Result<ClubGetListWithTicketsResponse> = repository.getFavourites(params!!)
}