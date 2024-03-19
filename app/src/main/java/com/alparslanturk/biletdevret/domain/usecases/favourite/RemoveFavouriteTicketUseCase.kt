package com.alparslanturk.biletdevret.domain.usecases.favourite

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.requests.favourite.RemoveFavouriteTicketRequest
import com.alparslanturk.biletdevret.domain.entities.responses.favourite.RemoveFavouriteTicketResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class RemoveFavouriteTicketUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<RemoveFavouriteTicketRequest, RemoveFavouriteTicketResponse>() {
    override suspend fun getData(params: RemoveFavouriteTicketRequest?): Result<RemoveFavouriteTicketResponse> = repository.removeFavouriteTicket(params!!)
}