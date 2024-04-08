package com.rammdakk.recharge.feature.catalog.view.model

data class CategoriesList(
    val cats: List<Category>,
    val onClick: (Int?) -> Unit
)

data class Category(
    val id: Int,
    val imagePath: String,
)
