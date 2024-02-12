package com.alparslanturk.kombineapp.domain.usecases

import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.requests.favourite.AddFavouriteClubRequest
import com.alparslanturk.kombineapp.domain.entities.requests.favourite.RemoveFavouriteClubRequest
import com.alparslanturk.kombineapp.domain.entities.requests.user.UserDeleteRequest
import com.alparslanturk.kombineapp.domain.entities.responses.favourite.AddFavouriteClubResponse
import com.alparslanturk.kombineapp.domain.entities.responses.favourite.RemoveFavouriteClubResponse
import com.alparslanturk.kombineapp.domain.entities.responses.user.deleteuser.UserDeleteResponse
import com.alparslanturk.kombineapp.domain.repository.Repository
import javax.inject.Inject

class RemoveFavouriteClubUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<RemoveFavouriteClubRequest, RemoveFavouriteClubResponse>() {
    override suspend fun getData(params: RemoveFavouriteClubRequest?): Result<RemoveFavouriteClubResponse> = repository.removeFavouriteClub(params!!)
}