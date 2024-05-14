package com.alparslanturk.bulbiletini.domain.usecases.favourite

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.favourite.AddFavouriteClubRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.favourite.AddFavouriteClubResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class AddFavouriteClubUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<AddFavouriteClubRequest, AddFavouriteClubResponse>() {
    override suspend fun getData(params: AddFavouriteClubRequest?): Result<AddFavouriteClubResponse> = repository.addFavouriteClub(params!!)
}