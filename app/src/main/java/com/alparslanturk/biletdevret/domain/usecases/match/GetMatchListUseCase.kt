package com.alparslanturk.biletdevret.domain.usecases.match

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.responses.match.GetMatchListResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetMatchListUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<Any, GetMatchListResponse>() {
    override suspend fun getData(params: Any?): Result<GetMatchListResponse> = repository.getMatchList()
}