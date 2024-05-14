package com.alparslanturk.bulbiletini.domain.entities.responses.club.clubgetdetailwithclubid

data class ClubGetDetailWithClubIdResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val data: ClubGetDetailWithClubIdResponseItem
)
