package com.alparslanturk.kombineapp.data.api

import com.alparslanturk.kombineapp.domain.entities.requests.favourite.AddFavouriteClubRequest
import com.alparslanturk.kombineapp.domain.entities.requests.favourite.AddFavouriteTicketRequest
import com.alparslanturk.kombineapp.domain.entities.requests.favourite.RemoveFavouriteClubRequest
import com.alparslanturk.kombineapp.domain.entities.requests.favourite.RemoveFavouriteTicketRequest
import com.alparslanturk.kombineapp.domain.entities.requests.tariff.PurchaseTariffRequest
import com.alparslanturk.kombineapp.domain.entities.requests.user.ForgotPasswordRequest
import com.alparslanturk.kombineapp.domain.entities.requests.user.RegisterRequest
import com.alparslanturk.kombineapp.domain.entities.requests.user.UserDeleteRequest
import com.alparslanturk.kombineapp.domain.entities.responses.club.clubgetlistwithtickets.ClubGetListWithTicketsResponse
import com.alparslanturk.kombineapp.domain.entities.responses.favourite.AddFavouriteClubResponse
import com.alparslanturk.kombineapp.domain.entities.responses.favourite.AddFavouriteTicketResponse
import com.alparslanturk.kombineapp.domain.entities.responses.favourite.RemoveFavouriteClubResponse
import com.alparslanturk.kombineapp.domain.entities.responses.favourite.RemoveFavouriteTicketResponse
import com.alparslanturk.kombineapp.domain.entities.responses.tariff.GetTariffListResponse
import com.alparslanturk.kombineapp.domain.entities.responses.tariff.PurchaseTariffResponse
import com.alparslanturk.kombineapp.domain.entities.responses.ticket.CreateTicketRequest
import com.alparslanturk.kombineapp.domain.entities.responses.ticket.CreateTicketResponse
import com.alparslanturk.kombineapp.domain.entities.responses.ticket.GetTicketsResponse
import com.alparslanturk.kombineapp.domain.entities.responses.user.deleteuser.UserDeleteResponse
import com.alparslanturk.kombineapp.domain.entities.responses.user.forgotpassword.ForgotPasswordResponse
import com.alparslanturk.kombineapp.domain.entities.responses.user.register.RegisterResponse
import com.alparslanturk.kombineapp.domain.entities.responses.user.verificationcode.VerificationCodeResponse
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

    @HTTP(method = "DELETE", path = "api/User/UserDelete", hasBody = true)
    suspend fun deleteUser(@Body userDeleteRequest: UserDeleteRequest): Response<UserDeleteResponse>

    //Club Services
    @GET("api/Club/ClubGetListWithTickets")
    suspend fun clubGetListWithTickets(@Query("userID") userID: String, @Query("filterType") filterType: Int): Response<ClubGetListWithTicketsResponse>

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

    //Tariff Services
    @GET("/api/Tariff/TariffGetList")
    suspend fun getTariffList(): Response<GetTariffListResponse>

    @POST("/api/Tariff/PurchaseFlowCreate")
    suspend fun purchaseTariff(@Body purchaseTariffRequest: PurchaseTariffRequest): Response<PurchaseTariffResponse>
}