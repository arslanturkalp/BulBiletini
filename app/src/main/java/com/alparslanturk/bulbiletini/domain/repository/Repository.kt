package com.alparslanturk.bulbiletini.domain.repository

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.favourite.AddFavouriteClubRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.favourite.AddFavouriteTicketRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.favourite.RemoveFavouriteClubRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.favourite.RemoveFavouriteTicketRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.profilecomment.AddCommentRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.tariff.PurchaseTariffRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.ticket.CreateTicketRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.ticket.NotifyTicketRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.user.ForgotPasswordRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.user.LoginRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.user.RegisterRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.user.UpdateUserInfoRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.user.UserDeleteRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.usermessage.SendMessageRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.userrate.RateUserRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.club.clubgetdetailwithclubid.ClubGetDetailWithClubIdResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.club.clubgetlistwithtickets.ClubGetListWithTicketsResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.favourite.AddFavouriteClubResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.favourite.AddFavouriteTicketResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.favourite.RemoveFavouriteClubResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.favourite.RemoveFavouriteTicketResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.match.GetMatchListResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.profilecomment.AddCommentResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.profilecomment.GetCommentsResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.profilecomment.GetMyCommentsResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.projectsettings.ProjectSettingsGetWithNameResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.suggestionandcomplaint.SendSuggestionAndComplaintResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.tariff.GetMyTariffsResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.tariff.GetTariffListResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.tariff.PurchaseTariffResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.ticket.CreateTicketResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.ticket.GetTicketsResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.ticket.NotifyTicketResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.user.blockuser.BlockUserResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.user.deleteuser.UserDeleteResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.user.forgotpassword.ForgotPasswordResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.user.getuserdetail.GetUserDetailResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.user.login.LoginResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.user.register.RegisterResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.user.updatenotificationtoken.UpdateNotificationTokenResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.user.updateuser.UpdateUserInfoResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.user.verificationcode.VerificationCodeResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.userblacklist.getblockedusers.GetBlockedUsersResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.usermessage.GetUserMessagesResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.usermessage.RetrieveMessagesResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.usermessage.SendMessageResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.userrate.RateUserResponse
import okhttp3.ResponseBody

interface Repository {

    suspend fun login(loginRequest: LoginRequest): Result<LoginResponse>

    suspend fun register(registerRequest: RegisterRequest): Result<RegisterResponse>

    suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): Result<ForgotPasswordResponse>

    suspend fun getVerificationCode(email: String): Result<VerificationCodeResponse>

    suspend fun deleteUser(userDeleteRequest: UserDeleteRequest): Result<UserDeleteResponse>

    suspend fun clubGetListWithTickets(userID: String, filterType: Int): Result<ClubGetListWithTicketsResponse>

    suspend fun clubGetDetailWithClubID(clubID: String, userID: String): Result<ClubGetDetailWithClubIdResponse>

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

    suspend fun getMyComments(createdUserID: String): Result<GetMyCommentsResponse>

    suspend fun addComment(addCommentRequest: AddCommentRequest): Result<AddCommentResponse>

    suspend fun rateUser(rateUserRequest: RateUserRequest): Result<RateUserResponse>

    suspend fun updateUserInfo(updateUserInfoRequest: UpdateUserInfoRequest): Result<UpdateUserInfoResponse>

    suspend fun getUserDetail(userID: String): Result<GetUserDetailResponse>

    suspend fun getBlockedUsers(userID: String): Result<GetBlockedUsersResponse>

    suspend fun blockUser(blockedUserID: String, blockedByUserID: String): Result<BlockUserResponse>

    suspend fun unBlockUser(blockedUserID: String, blockedByUserID: String): Result<BlockUserResponse>

    suspend fun notifyTicket(notifyTicketRequest: NotifyTicketRequest): Result<NotifyTicketResponse>

    suspend fun loginTest(): Result<ResponseBody>

    suspend fun projectSettingsGetWithName(settingName: String): Result<ProjectSettingsGetWithNameResponse>

    suspend fun userUpdateNotificationToken(userId: String, notificationToken: String): Result<UpdateNotificationTokenResponse>

    suspend fun sendSuggestionAndComplaint(userID: String, requestText: String): Result<SendSuggestionAndComplaintResponse>
}