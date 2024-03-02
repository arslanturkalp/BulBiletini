package com.alparslanturk.kombineapp.domain.repository

import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.requests.favourite.AddFavouriteClubRequest
import com.alparslanturk.kombineapp.domain.entities.requests.favourite.AddFavouriteTicketRequest
import com.alparslanturk.kombineapp.domain.entities.requests.favourite.RemoveFavouriteClubRequest
import com.alparslanturk.kombineapp.domain.entities.requests.favourite.RemoveFavouriteTicketRequest
import com.alparslanturk.kombineapp.domain.entities.requests.profilecomment.AddCommentRequest
import com.alparslanturk.kombineapp.domain.entities.requests.tariff.PurchaseTariffRequest
import com.alparslanturk.kombineapp.domain.entities.requests.user.ForgotPasswordRequest
import com.alparslanturk.kombineapp.domain.entities.requests.user.LoginRequest
import com.alparslanturk.kombineapp.domain.entities.requests.user.RegisterRequest
import com.alparslanturk.kombineapp.domain.entities.requests.user.UserDeleteRequest
import com.alparslanturk.kombineapp.domain.entities.requests.usermessage.SendMessageRequest
import com.alparslanturk.kombineapp.domain.entities.responses.club.clubgetlistwithtickets.ClubGetListWithTicketsResponse
import com.alparslanturk.kombineapp.domain.entities.responses.favourite.AddFavouriteClubResponse
import com.alparslanturk.kombineapp.domain.entities.responses.favourite.AddFavouriteTicketResponse
import com.alparslanturk.kombineapp.domain.entities.responses.favourite.RemoveFavouriteClubResponse
import com.alparslanturk.kombineapp.domain.entities.responses.favourite.RemoveFavouriteTicketResponse
import com.alparslanturk.kombineapp.domain.entities.responses.match.GetMatchListResponse
import com.alparslanturk.kombineapp.domain.entities.responses.profilecomment.GetCommentsResponse
import com.alparslanturk.kombineapp.domain.entities.responses.tariff.GetMyTariffsResponse
import com.alparslanturk.kombineapp.domain.entities.responses.tariff.GetTariffListResponse
import com.alparslanturk.kombineapp.domain.entities.responses.tariff.PurchaseTariffResponse
import com.alparslanturk.kombineapp.domain.entities.requests.ticket.CreateTicketRequest
import com.alparslanturk.kombineapp.domain.entities.responses.profilecomment.AddCommentResponse
import com.alparslanturk.kombineapp.domain.entities.responses.ticket.CreateTicketResponse
import com.alparslanturk.kombineapp.domain.entities.responses.ticket.GetTicketsResponse
import com.alparslanturk.kombineapp.domain.entities.responses.user.deleteuser.UserDeleteResponse
import com.alparslanturk.kombineapp.domain.entities.responses.user.forgotpassword.ForgotPasswordResponse
import com.alparslanturk.kombineapp.domain.entities.responses.user.login.LoginResponse
import com.alparslanturk.kombineapp.domain.entities.responses.user.register.RegisterResponse
import com.alparslanturk.kombineapp.domain.entities.responses.user.verificationcode.VerificationCodeResponse
import com.alparslanturk.kombineapp.domain.entities.responses.usermessage.GetUserMessagesResponse
import com.alparslanturk.kombineapp.domain.entities.responses.usermessage.RetrieveMessagesResponse
import com.alparslanturk.kombineapp.domain.entities.responses.usermessage.SendMessageResponse

interface Repository {

    suspend fun login(loginRequest: LoginRequest): Result<LoginResponse>

    suspend fun register(registerRequest: RegisterRequest): Result<RegisterResponse>

    suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): Result<ForgotPasswordResponse>

    suspend fun getVerificationCode(email: String): Result<VerificationCodeResponse>

    suspend fun deleteUser(userDeleteRequest: UserDeleteRequest): Result<UserDeleteResponse>

    suspend fun clubGetListWithTickets(userID: String, filterType: Int): Result<ClubGetListWithTicketsResponse>

    suspend fun addFavouriteClub(addFavouriteClubRequest: AddFavouriteClubRequest): Result<AddFavouriteClubResponse>

    suspend fun removeFavouriteClub(removeFavouriteClubRequest: RemoveFavouriteClubRequest): Result<RemoveFavouriteClubResponse>

    suspend fun addFavouriteTicket(addFavouriteTicketRequest: AddFavouriteTicketRequest): Result<AddFavouriteTicketResponse>

    suspend fun removeFavouriteTicket(removeFavouriteTicketRequest: RemoveFavouriteTicketRequest): Result<RemoveFavouriteTicketResponse>

    suspend fun getFavourites(userID: String): Result<ClubGetListWithTicketsResponse>

    suspend fun getTickets(userID: String): Result<GetTicketsResponse>

    suspend fun getMatchTickets(matchID: String, userID: String): Result<GetTicketsResponse>

    suspend fun createTicket(createTicketRequest: CreateTicketRequest): Result<CreateTicketResponse>

    suspend fun getTariffList(): Result<GetTariffListResponse>

    suspend fun purchaseTariff(purchaseTariffRequest: PurchaseTariffRequest): Result<PurchaseTariffResponse>

    suspend fun getMatchList(): Result<GetMatchListResponse>

    suspend fun getUserMessages(userID: String): Result<GetUserMessagesResponse>

    suspend fun retrieveMessages(userOne: String, userTwo: String): Result<RetrieveMessagesResponse>

    suspend fun sendMessage(sendMessageRequest: SendMessageRequest): Result<SendMessageResponse>

    suspend fun getMyTariffs(userID: String): Result<GetMyTariffsResponse>

    suspend fun getComments(userID: String): Result<GetCommentsResponse>

    suspend fun addComment(addCommentRequest: AddCommentRequest): Result<AddCommentResponse>
}