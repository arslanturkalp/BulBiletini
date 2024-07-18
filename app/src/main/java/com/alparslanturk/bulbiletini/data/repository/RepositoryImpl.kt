package com.alparslanturk.bulbiletini.data.repository

import com.alparslanturk.bulbiletini.data.api.ApiService
import com.alparslanturk.bulbiletini.data.api.ForTokenApiService
import com.alparslanturk.bulbiletini.data.entities.models.Result
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
import com.alparslanturk.bulbiletini.domain.repository.Repository
import okhttp3.ResponseBody
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: ApiService, private val forTokenApiService: ForTokenApiService) : Repository {
    override suspend fun login(loginRequest: LoginRequest): Result<LoginResponse> {
        val response = forTokenApiService.login(loginRequest)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun register(registerRequest: RegisterRequest): Result<RegisterResponse> {
        val response = apiService.register(registerRequest)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): Result<ForgotPasswordResponse> {
        val response = apiService.forgotPassword(forgotPasswordRequest)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun getVerificationCode(email: String): Result<VerificationCodeResponse> {
        val response = apiService.getVerificationCode(email)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun deleteUser(userDeleteRequest: UserDeleteRequest): Result<UserDeleteResponse> {
        val response = apiService.deleteUser(userDeleteRequest)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun clubGetListWithTickets(userID: String, filterType: Int): Result<ClubGetListWithTicketsResponse> {
        val response = apiService.clubGetListWithTickets(userID, filterType)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun clubGetDetailWithClubID(clubID: String, userID: String): Result<ClubGetDetailWithClubIdResponse> {
        val response = apiService.clubGetDetailWithClubId(clubID, userID)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun addFavouriteClub(addFavouriteClubRequest: AddFavouriteClubRequest): Result<AddFavouriteClubResponse> {
        val response = apiService.addFavouriteClub(addFavouriteClubRequest)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun removeFavouriteClub(removeFavouriteClubRequest: RemoveFavouriteClubRequest): Result<RemoveFavouriteClubResponse> {
        val response = apiService.removeFavouriteClub(removeFavouriteClubRequest)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun addFavouriteTicket(addFavouriteTicketRequest: AddFavouriteTicketRequest): Result<AddFavouriteTicketResponse> {
        val response = apiService.addFavouriteTicket(addFavouriteTicketRequest)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun removeFavouriteTicket(removeFavouriteTicketRequest: RemoveFavouriteTicketRequest): Result<RemoveFavouriteTicketResponse> {
        val response = apiService.removeFavouriteTicket(removeFavouriteTicketRequest)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun getFavourites(userID: String): Result<ClubGetListWithTicketsResponse> {
        val response = apiService.getFavourites(userID)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun getTickets(userID: String): Result<GetTicketsResponse> {
        val response = apiService.getTickets(userID)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun getMatchTickets(matchID: String, userID: String): Result<GetTicketsResponse> {
        val response = apiService.getMatchTickets(matchID, userID)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun createTicket(createTicketRequest: CreateTicketRequest): Result<CreateTicketResponse> {
        val response = apiService.createTicket(createTicketRequest)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun getTariffList(): Result<GetTariffListResponse> {
        val response = apiService.getTariffList()
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun purchaseTariff(purchaseTariffRequest: PurchaseTariffRequest): Result<PurchaseTariffResponse> {
        val response = apiService.purchaseTariff(purchaseTariffRequest)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun getMatchList(): Result<GetMatchListResponse> {
        val response = apiService.getMatchList()
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun getUserMessages(userID: String): Result<GetUserMessagesResponse> {
        val response = apiService.getUserMessages(userID)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun retrieveMessages(userOne: String, userTwo: String): Result<RetrieveMessagesResponse> {
        val response = apiService.retrieveMessages(userOne, userTwo)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun sendMessage(sendMessageRequest: SendMessageRequest): Result<SendMessageResponse> {
        val response = apiService.sendMessage(sendMessageRequest)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun getMyTariffs(userID: String): Result<GetMyTariffsResponse> {
        val response = apiService.getMyTariffs(userID)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun getComments(userID: String): Result<GetCommentsResponse> {
        val response = apiService.getComments(userID)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun getMyComments(createdUserID: String): Result<GetMyCommentsResponse> {
        val response = apiService.getMyComments(createdUserID)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun addComment(addCommentRequest: AddCommentRequest): Result<AddCommentResponse> {
        val response = apiService.addComment(addCommentRequest)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun rateUser(rateUserRequest: RateUserRequest): Result<RateUserResponse> {
        val response = apiService.rateUser(rateUserRequest)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun updateUserInfo(updateUserInfoRequest: UpdateUserInfoRequest): Result<UpdateUserInfoResponse> {
        val response = apiService.updateUserInfo(updateUserInfoRequest)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun getUserDetail(userID: String): Result<GetUserDetailResponse> {
        val response = apiService.getUserDetail(userID)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun getBlockedUsers(userID: String): Result<GetBlockedUsersResponse> {
        val response = apiService.getBlockedUsers(userID)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }


    override suspend fun blockUser(blockedUserID: String, blockedByUserID: String): Result<BlockUserResponse> {
        val response = apiService.blockUser(blockedUserID, blockedByUserID)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun unBlockUser(blockedUserID: String, blockedByUserID: String): Result<BlockUserResponse> {
        val response = apiService.unBlockUser(blockedUserID, blockedByUserID)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun notifyTicket(notifyTicketRequest: NotifyTicketRequest): Result<NotifyTicketResponse> {
        val response = apiService.notifyTicket(notifyTicketRequest)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun loginTest(): Result<ResponseBody> {
        val response = apiService.testLogin()
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun projectSettingsGetWithName(settingName: String): Result<ProjectSettingsGetWithNameResponse> {
        val response = apiService.projectSettingsGetWithName(settingName)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun userUpdateNotificationToken(userId: String, notificationToken: String): Result<UpdateNotificationTokenResponse> {
        val response = apiService.updateNotificationToken(userId, notificationToken)
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }

    override suspend fun sendSuggestionAndComplaint(userID: String, requestText: String): Result<SendSuggestionAndComplaintResponse> {
        val response = apiService.sendSuggestionAndComplaint(SendSuggestionAndComplaintRequest(userID, requestText))
        return try {
            return if (response.isSuccessful) {
                Result.Success(response.body(), response.code(), response.message())
            } else Result.Error(response.code(), response.message())
        } catch (e: Exception) {
            Result.Error(response.code(), e.message)
        }
    }
}