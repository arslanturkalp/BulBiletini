package com.alparslanturk.biletdevret.domain.usecases.club

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.requests.club.ClubGetDetailWithClubIdRequest
import com.alparslanturk.biletdevret.domain.entities.responses.club.clubgetdetailwithclubid.ClubGetDetailWithClubIdResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class ClubGetDetailWithClubIdUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<ClubGetDetailWithClubIdRequest, ClubGetDetailWithClubIdResponse>() {
    override suspend fun getData(params: ClubGetDetailWithClubIdRequest?): Result<ClubGetDetailWithClubIdResponse> = repository.clubGetDetailWithClubID(params?.clubID!!, params.userID)
}