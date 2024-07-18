package com.alparslanturk.bulbiletini.data.api

import com.alparslanturk.bulbiletini.domain.entities.requests.favourite.AddFavouriteClubRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.favourite.AddFavouriteTicketRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.favourite.RemoveFavouriteClubRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.favourite.RemoveFavouriteTicketRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.profilecomment.AddCommentRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.suggestionandcomplaint.SendSuggestionAndComplaintRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.tariff.PurchaseTariffRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.ticket.CreateTicketRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.ticket.NotifyTicketRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.user.ForgotPasswordRequest
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
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    //User Services
    @POST("api/User/UserRegister")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @POST("api/User/UserForgotPassword")
    suspend fun forgotPassword(@Body forgotPasswordRequest: ForgotPasswordRequest): Response<ForgotPasswordResponse>

    @POST("api/User/UserSetVerificationCode")
    suspend fun getVerificationCode(@Query("email") email: String): Response<VerificationCodeResponse>

    @POST("api/User/UserDelete")
    suspend fun deleteUser(@Body userDeleteRequest: UserDeleteRequest): Response<UserDeleteResponse>

    @POST("api/User/UserUpdateInfo")
    suspend fun updateUserInfo(@Body updateUserInfoRequest: UpdateUserInfoRequest): Response<UpdateUserInfoResponse>

    @POST("api/User/UserBlock")
    suspend fun blockUser(@Query("userId") userID: String): Response<BlockUserResponse>

    @GET("api/User/UserGetDetail")
    suspend fun getUserDetail(@Query("userId") userID: String): Response<GetUserDetailResponse>

    @POST("api/User/UserUpdateNotificationToken")
    suspend fun updateNotificationToken(@Query("userId") userID: String, @Query("notificationToken") notificationToken: String): Response<UpdateNotificationTokenResponse>

    //Club Services
    @GET("api/Club/ClubGetListWithTickets")
    suspend fun clubGetListWithTickets(@Query("userID") userID: String, @Query("filterType") filterType: Int): Response<ClubGetListWithTicketsResponse>

    @GET("api/Club/ClubGetDetailWithClubId")
    suspend fun clubGetDetailWithClubId(@Query("clubID") clubID: String, @Query("userID") userID: String): Response<ClubGetDetailWithClubIdResponse>

    //Match Services
    @GET("/api/Match/MatchGetList")
    suspend fun getMatchList(): Response<GetMatchListResponse>

    //Favourite Services
    @POST("api/Favourite/FavouriteClubAdd")
    suspend fun addFavouriteClub(@Body addFavouriteClubRequest: AddFavouriteClubRequest): Response<AddFavouriteClubResponse>

    @POST("api/Favourite/FavouriteClubRemove")
    suspend fun removeFavouriteClub(@Body removeFavouriteClubRequest: RemoveFavouriteClubRequest): Response<RemoveFavouriteClubResponse>

    @POST("api/Favourite/FavouriteTicketAdd")
    suspend fun addFavouriteTicket(@Body addFavouriteTicketRequest: AddFavouriteTicketRequest): Response<AddFavouriteTicketResponse>

    @POST("api/Favourite/FavouriteTicketRemove")
    suspend fun removeFavouriteTicket(@Body removeFavouriteTicketRequest: RemoveFavouriteTicketRequest): Response<RemoveFavouriteTicketResponse>

    @GET("api/Favourite/GetFavourites")
    suspend fun getFavourites(@Query("userID") userID: String): Response<ClubGetListWithTicketsResponse>

    //Ticket Services
    @POST("/api/Ticket/TicketCreate")
    suspend fun createTicket(@Body createTicketRequest: CreateTicketRequest): Response<CreateTicketResponse>

    @GET("api/Ticket/TicketGetListWithUserId")
    suspend fun getTickets(@Query("userID") userID: String): Response<GetTicketsResponse>

    @GET("/api/Ticket/TicketGetListWithMatchId")
    suspend fun getMatchTickets(@Query("matchID") matchID: String, @Query("userID") userID: String): Response<GetTicketsResponse>

    @POST("/api/Ticket/TicketNotify")
    suspend fun notifyTicket(@Body notifyTicketRequest: NotifyTicketRequest): Response<NotifyTicketResponse>

    //ProfileCommentServices
    @GET("/api/ProfileComment/ProfileCommentGetListWithUserId")
    suspend fun getComments(@Query("userId") userID: String): Response<GetCommentsResponse>

    @GET("/api/ProfileComment/ProfileCommentGetListWithCreatedUserId")
    suspend fun getMyComments(@Query("createdUserId") userID: String): Response<GetMyCommentsResponse>

    @POST("/api/ProfileComment/ProfileCommentCreate")
    suspend fun addComment(@Body addCommentRequest: AddCommentRequest): Response<AddCommentResponse>

    //Tariff Services
    @GET("/api/Tariff/TariffGetList")
    suspend fun getTariffList(): Response<GetTariffListResponse>

    //PurchaseFlow Services
    @POST("/api/PurchaseFlow/PurchaseFlowCreate")
    suspend fun purchaseTariff(@Body purchaseTariffRequest: PurchaseTariffRequest): Response<PurchaseTariffResponse>

    @GET("/api/PurchaseFlow/PurchaseFlowGetListWithUserId")
    suspend fun getMyTariffs(@Query("userId") userID: String): Response<GetMyTariffsResponse>

    //UserBlackList Services
    @GET("/api/UserBlackList/UserBlackListGetListWithUserId")
    suspend fun getBlockedUsers(@Query("userId") userID: String): Response<GetBlockedUsersResponse>

    @POST("/api/UserBlackList/UserBlackListCreate")
    suspend fun blockUser(@Query("blockedUserId") blockedUserId: String, @Query("blockedByUserId") blockedByUserId: String): Response<BlockUserResponse>

    @POST("/api/UserBlackList/UserBlackListRemove")
    suspend fun unBlockUser(@Query("blockedUserId") blockedUserId: String, @Query("blockedByUserId") blockedByUserId: String): Response<BlockUserResponse>

    //UserMessage Services
    @GET("/api/UserMessage/UserMessageGetContactWithUserId")
    suspend fun getUserMessages(@Query("userId") userID: String): Response<GetUserMessagesResponse>

    @GET("/api/UserMessage/UserMessageRetrieve")
    suspend fun retrieveMessages(@Query("userOne") userOne: String, @Query("userTwo") userTwo: String): Response<RetrieveMessagesResponse>

    @POST("/api/UserMessage/UserMessageSend")
    suspend fun sendMessage(@Body sendMessageRequest: SendMessageRequest): Response<SendMessageResponse>

    //UserRate Services
    @POST("/api/UserRate/UserRateCreate")
    suspend fun rateUser(@Body rateUserRequest: RateUserRequest): Response<RateUserResponse>

    @GET("/UserIsLoginTest")
    suspend fun testLogin(): Response<ResponseBody>

    //ProjectSettings Services
    @GET("/api/ProjectSettings/ProjectSettingsGetWithName")
    suspend fun projectSettingsGetWithName(@Query("settingName") settingName: String): Response<ProjectSettingsGetWithNameResponse>

    //SendSuggestionAndComplaintServices
    @POST("/api/SuggestionAndComplaint/SuggestionAndComplaintCreate")
    suspend fun sendSuggestionAndComplaint(@Body sendSuggestionAndComplaintRequest: SendSuggestionAndComplaintRequest): Response<SendSuggestionAndComplaintResponse>

}