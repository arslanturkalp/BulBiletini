package com.alparslanturk.bulbiletini.data.entities.models

data class SelectionDialogItem(
    val name: String,
    val id: String = "",
    val value: String? = null,
    val resId: Int = 0,
    val textColor: String? = null
)