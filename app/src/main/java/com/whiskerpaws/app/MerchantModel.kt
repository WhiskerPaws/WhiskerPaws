package com.whiskerpaws.app

data class MerchantModel(
    val name: String,
    val url: String,
    val logoDrawableId: Int = android.R.drawable.sym_def_app_icon
)
