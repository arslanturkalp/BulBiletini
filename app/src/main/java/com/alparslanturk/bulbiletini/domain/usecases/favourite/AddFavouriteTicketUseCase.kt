package com.alparslanturk.bulbiletini.domain.usecases.favourite

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.favourite.AddFavouriteTicketRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.favourite.AddFavouriteTicketResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class AddFavouriteTicketUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<AddFavouriteTicketRequest, AddFavouriteTicketResponse>() {
    override suspend fun getData(params: AddFavouriteTicketRequest?): Result<AddFavouriteTicketResponse> = repository.addFavouriteTicket(params!!)
}