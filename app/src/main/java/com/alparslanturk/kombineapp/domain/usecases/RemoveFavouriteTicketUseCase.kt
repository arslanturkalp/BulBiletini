package com.alparslanturk.kombineapp.domain.usecases

import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.requests.favourite.RemoveFavouriteTicketRequest
import com.alparslanturk.kombineapp.domain.entities.responses.favourite.RemoveFavouriteTicketResponse
import com.alparslanturk.kombineapp.domain.repository.Repository
import com.alparslanturk.kombineapp.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class RemoveFavouriteTicketUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<RemoveFavouriteTicketRequest, RemoveFavouriteTicketResponse>() {
    override suspend fun getData(params: RemoveFavouriteTicketRequest?): Result<RemoveFavouriteTicketResponse> = repository.removeFavouriteTicket(params!!)
}