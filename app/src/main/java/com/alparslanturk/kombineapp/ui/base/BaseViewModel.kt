package com.alparslanturk.kombineapp.ui.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    private val errorList: MutableList<Int> = mutableListOf()

    fun clearErrorList() = errorList.clear()

    fun addError(error: Int) = errorList.add(error)

    fun getErrorList() = errorList

    override fun onCleared() {
        super.onCleared()
    }
}