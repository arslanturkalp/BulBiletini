package com.alparslanturk.biletdevret.domain.usecases.favourite

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.requests.favourite.AddFavouriteTicketRequest
import com.alparslanturk.biletdevret.domain.entities.responses.favourite.AddFavouriteTicketResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class AddFavouriteTicketUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<AddFavouriteTicketRequest, AddFavouriteTicketResponse>() {
    override suspend fun getData(params: AddFavouriteTicketRequest?): Result<AddFavouriteTicketResponse> = repository.addFavouriteTicket(params!!)
}