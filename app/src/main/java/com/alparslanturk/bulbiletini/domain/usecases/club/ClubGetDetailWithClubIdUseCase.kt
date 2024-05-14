package com.alparslanturk.bulbiletini.domain.usecases.club

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.club.ClubGetDetailWithClubIdRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.club.clubgetdetailwithclubid.ClubGetDetailWithClubIdResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class ClubGetDetailWithClubIdUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<ClubGetDetailWithClubIdRequest, ClubGetDetailWithClubIdResponse>() {
    override suspend fun getData(params: ClubGetDetailWithClubIdRequest?): Result<ClubGetDetailWithClubIdResponse> = repository.clubGetDetailWithClubID(params?.clubID!!, params.userID)
}