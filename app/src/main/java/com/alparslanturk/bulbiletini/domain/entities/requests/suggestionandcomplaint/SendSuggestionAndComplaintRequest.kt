package com.alparslanturk.bulbiletini.domain.entities.requests.suggestionandcomplaint

data class SendSuggestionAndComplaintRequest(
    val userId: String,
    val requestText: String
)
