package com.books.android.booksapp.model

import java.util.Date

data class Book(
    val title: String,
    val author: String,
    val numberOfPages: Int,
    val publishedOn: Date,
    val description: String

)