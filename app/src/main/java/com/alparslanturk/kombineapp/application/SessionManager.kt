package com.alparslanturk.kombineapp.application

import com.orhanobut.hawk.Hawk

object SessionManager {

    private const val KEY_UNKNOWN_ANSWER = "KEY_UNKNOWN_ANSWER"
    private const val KEY_UNKNOWN_IMAGE = "KEY_UNKNOWN_IMAGE"
    private const val KEY_IS_SHOWED_FLAG = "KEY_IS_SHOWED_FLAG"
    private const val KEY_IS_SHOWED_NUMBER = "KEY_IS_SHOWED_NUMBER"
    private const val KEY_IS_SHOWED_TUTORIAL = "KEY_IS_SHOWED_TUTORIAL"
    private const val KEY_IS_USED_EXTRA_LIFE = "KEY_IS_USED_EXTRA_LIFE"
    private const val KEY_IS_ONLINE_MODE_ACTIVE = "KEY_IS_ONLINE_MODE_ACTIVE"
    private const val KEY_IS_MUSIC_OPEN = "KEY_IS_MUSIC_OPEN"
    private const val KEY_IS_SOUND_OPEN = "KEY_IS_SOUND_OPEN"

    private const val KEY_TOKEN = "KEY_TOKEN"
    private const val KEY_REFRESH_TOKEN = "KEY_REFRESH_TOKEN"

    private const val KEY_SCORE = "KEY_SCORE"
    private const val KEY_WRONG_COUNT = "KEY_WRONG_COUNT"

    private const val KEY_USER_ID = "KEY_USER_ID"
    private const val KEY_USER_NAME = "KEY_USER_NAME"
    private const val KEY_PASSWORD = "KEY_PASSWORD"

    private var unknownAnswer: String = ""
    private var unknownImage: String = ""
    private var token: String = ""
    private var refreshToken: String = ""

    private var userID: Int = 0
    private var score: Int = 0
    private var wrongCount: Int = 0

    private var userName: String = ""
    private var password: String = ""

    private var isShowedFlag: Boolean = false
    private var isShowedNumber: Boolean = false
    private var isShowedTutorial: Boolean = false
    private var isUsedExtraLife: Boolean = false
    private var isOnlineModeActive: Boolean = false
    private var isMusicOpen: Boolean = true
    private var isSoundOpen: Boolean = true

    init {
        unknownAnswer = Hawk.get(KEY_UNKNOWN_ANSWER, "")
        unknownImage = Hawk.get(KEY_UNKNOWN_IMAGE, "")
        token = Hawk.get(KEY_TOKEN, "")
        refreshToken = Hawk.get(KEY_REFRESH_TOKEN, "")
        userID = Hawk.get(KEY_USER_ID, 0)
        score = Hawk.get(KEY_SCORE, 0)
        wrongCount = Hawk.get(KEY_WRONG_COUNT, 0)
        userName = Hawk.get(KEY_USER_NAME, "")
        password = Hawk.get(KEY_PASSWORD, "")
        isShowedFlag = Hawk.get(KEY_IS_SHOWED_FLAG, false)
        isShowedNumber = Hawk.get(KEY_IS_SHOWED_NUMBER, false)
        isShowedTutorial = Hawk.get(KEY_IS_SHOWED_TUTORIAL, false)
        isUsedExtraLife = Hawk.get(KEY_IS_USED_EXTRA_LIFE, false)
        isOnlineModeActive = Hawk.get(KEY_IS_ONLINE_MODE_ACTIVE, false)
        isMusicOpen = Hawk.get(KEY_IS_MUSIC_OPEN, true)
        isSoundOpen = Hawk.get(KEY_IS_SOUND_OPEN, true)
    }

    fun updateUnknownAnswer(value: String) {
        Hawk.put(KEY_UNKNOWN_ANSWER, value)
        unknownAnswer = value
    }

    fun getUnknownAnswer() = unknownAnswer

    fun clearUnknownAnswer() {
        updateUnknownAnswer("")
    }

    fun updateUnknownImage(value: String) {
        Hawk.put(KEY_UNKNOWN_IMAGE, value)
        unknownImage = value
    }

    fun getUnknownImage() = unknownImage

    fun clearUnknownImage() {
        updateUnknownImage("")
    }

    fun updateToken(value: String) {
        Hawk.put(KEY_TOKEN, value)
        token = value
    }

    fun getToken() = token

    fun clearToken() {
        updateToken("")
    }

    fun updateRefreshToken(value: String) {
        Hawk.put(KEY_REFRESH_TOKEN, value)
        refreshToken = value
    }

    fun getRefreshToken() = refreshToken

    fun clearRefreshToken() {
        updateRefreshToken("")
    }

    fun updateUserID(value: Int) {
        Hawk.put(KEY_USER_ID, value)
        userID = value
    }

    fun getUserID() = userID

    fun isAdminUser() = getUserID() == 13

    fun clearUserID() {
        updateUserID(0)
    }

    fun updateScore(value: Int) {
        Hawk.put(KEY_SCORE, value)
        score = value
    }

    fun getScore() = score

    fun clearScore() {
        score = 0
    }

    fun updateWrongCount(value: Int) {
        Hawk.put(KEY_WRONG_COUNT, value)
        wrongCount = value
    }

    fun getWrongCount() = wrongCount

    fun clearWrongCount() {
        wrongCount = 0
    }

    fun updateUserName(value: String) {
        Hawk.put(KEY_USER_NAME, value)
        userName = value
    }

    fun getUserName() = userName

    fun clearUserName() {
        updateUserName("")
    }

    fun updatePassword(value: String) {
        Hawk.put(KEY_PASSWORD, value)
        password = value
    }

    fun getPassword() = password

    fun clearPassword() {
        updatePassword("")
    }

    fun updateIsShowedFlag(value: Boolean) {
        Hawk.put(KEY_IS_SHOWED_FLAG, value)
        isShowedFlag = value
    }

    fun getIsShowedFlag() = isShowedFlag

    fun clearIsShowedFlag() {
        updateIsShowedFlag(false)
    }

    fun updateIsShowedNumber(value: Boolean) {
        Hawk.put(KEY_IS_SHOWED_NUMBER, value)
        isShowedNumber = value
    }

    fun getIsShowedNumber() = isShowedNumber

    fun clearIsShowedNumber() {
        updateIsShowedNumber(false)
    }

    fun updateIsShowedTutorial(value: Boolean) {
        Hawk.put(KEY_IS_SHOWED_TUTORIAL, value)
        isShowedTutorial = value
    }

    fun getIsShowedTutorial() = isShowedTutorial

    fun clearIsShowedTutorial() {
        updateIsShowedTutorial(false)
    }

    fun updateIsUsedExtraLife(value: Boolean) {
        Hawk.put(KEY_IS_USED_EXTRA_LIFE, value)
        isUsedExtraLife = value
    }

    fun getIsUsedExtraLife() = isUsedExtraLife

    fun clearIsUsedExtraLife() {
        updateIsUsedExtraLife(false)
    }

    fun updateIsOnlineModeActive(value: Boolean) {
        Hawk.put(KEY_IS_ONLINE_MODE_ACTIVE, value)
        isOnlineModeActive = value
    }

    fun getIsOnlineModeActive() = isOnlineModeActive

    fun clearIsOnlineModeActive() {
        updateIsOnlineModeActive(false)
    }

    fun updateIsMusicOpen(value: Boolean) {
        Hawk.put(KEY_IS_MUSIC_OPEN, value)
        isMusicOpen = value
    }

    fun getIsMusicOpen() = isMusicOpen

    fun clearIsMusicOpen() {
        updateIsMusicOpen(true)
    }

    fun updateIsSoundOpen(value: Boolean) {
        Hawk.put(KEY_IS_SOUND_OPEN, value)
        isSoundOpen = value
    }

    fun getIsSoundOpen() = isSoundOpen

    fun clearIsSoundOpen() {
        updateIsSoundOpen(true)
    }

}