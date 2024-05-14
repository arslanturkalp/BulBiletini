package com.alparslanturk.bulbiletini.domain.usecases.match

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.responses.match.GetMatchListResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetMatchListUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<Any, GetMatchListResponse>() {
    override suspend fun getData(params: Any?): Result<GetMatchListResponse> = repository.getMatchList()
}