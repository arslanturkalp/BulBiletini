package com.alparslanturk.bulbiletini.domain.entities.requests.user

data class UpdateUserInfoRequest(
    val userId: String,
    val newProfilePhoto: String?,
    val newName: String?,
    val newSurname: String?,
    val newPhoneNumber: String?,
    val newIsShownPhoneNumber: Boolean
)
