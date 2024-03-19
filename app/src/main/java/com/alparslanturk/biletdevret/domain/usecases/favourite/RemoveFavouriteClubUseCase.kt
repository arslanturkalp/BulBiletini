package com.alparslanturk.biletdevret.domain.usecases.favourite

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.requests.favourite.RemoveFavouriteClubRequest
import com.alparslanturk.biletdevret.domain.entities.responses.favourite.RemoveFavouriteClubResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class RemoveFavouriteClubUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<RemoveFavouriteClubRequest, RemoveFavouriteClubResponse>() {
    override suspend fun getData(params: RemoveFavouriteClubRequest?): Result<RemoveFavouriteClubResponse> = repository.removeFavouriteClub(params!!)
}