package com.books.android.booksapp.ui.main

import com.books.android.booksapp.network.BooksService
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val booksService: BooksService
) {

}