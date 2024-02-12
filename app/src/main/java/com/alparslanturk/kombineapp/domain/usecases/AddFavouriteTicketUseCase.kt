package com.alparslanturk.kombineapp.domain.usecases

import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.requests.favourite.AddFavouriteTicketRequest
import com.alparslanturk.kombineapp.domain.entities.responses.favourite.AddFavouriteTicketResponse
import com.alparslanturk.kombineapp.domain.repository.Repository
import javax.inject.Inject

class AddFavouriteTicketUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<AddFavouriteTicketRequest, AddFavouriteTicketResponse>() {
    override suspend fun getData(params: AddFavouriteTicketRequest?): Result<AddFavouriteTicketResponse> = repository.addFavouriteTicket(params!!)
}