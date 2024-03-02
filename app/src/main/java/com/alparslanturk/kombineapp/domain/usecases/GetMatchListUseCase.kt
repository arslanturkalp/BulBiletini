package com.alparslanturk.kombineapp.domain.usecases

import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.responses.match.GetMatchListResponse
import com.alparslanturk.kombineapp.domain.repository.Repository
import com.alparslanturk.kombineapp.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetMatchListUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<Any, GetMatchListResponse>() {
    override suspend fun getData(params: Any?): Result<GetMatchListResponse> = repository.getMatchList()
}