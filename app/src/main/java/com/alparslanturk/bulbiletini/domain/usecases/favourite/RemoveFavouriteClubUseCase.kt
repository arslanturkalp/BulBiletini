package com.alparslanturk.bulbiletini.domain.usecases.favourite

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.favourite.RemoveFavouriteClubRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.favourite.RemoveFavouriteClubResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class RemoveFavouriteClubUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<RemoveFavouriteClubRequest, RemoveFavouriteClubResponse>() {
    override suspend fun getData(params: RemoveFavouriteClubRequest?): Result<RemoveFavouriteClubResponse> = repository.removeFavouriteClub(params!!)
}