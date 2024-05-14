package com.alparslanturk.bulbiletini.domain.usecases.favourite

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.favourite.RemoveFavouriteTicketRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.favourite.RemoveFavouriteTicketResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class RemoveFavouriteTicketUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<RemoveFavouriteTicketRequest, RemoveFavouriteTicketResponse>() {
    override suspend fun getData(params: RemoveFavouriteTicketRequest?): Result<RemoveFavouriteTicketResponse> = repository.removeFavouriteTicket(params!!)
}