package com.alparslanturk.biletdevret.domain.usecases.favourite

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.requests.favourite.AddFavouriteClubRequest
import com.alparslanturk.biletdevret.domain.entities.responses.favourite.AddFavouriteClubResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class AddFavouriteClubUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<AddFavouriteClubRequest, AddFavouriteClubResponse>() {
    override suspend fun getData(params: AddFavouriteClubRequest?): Result<AddFavouriteClubResponse> = repository.addFavouriteClub(params!!)
}