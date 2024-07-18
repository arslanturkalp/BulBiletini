package com.alparslanturk.bulbiletini.domain.entities.responses.suggestionandcomplaint

data class SendSuggestionAndComplaintResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String
)