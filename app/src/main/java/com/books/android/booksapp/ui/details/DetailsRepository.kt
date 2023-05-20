package com.books.android.booksapp.ui.details

import com.books.android.booksapp.model.Book
import com.books.android.booksapp.network.BooksService
import com.books.android.booksapp.persistence.BookDao
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val bookDao: BookDao
) {

    suspend fun getBookById(id: String): Book? {
        return bookDao.getBook(id)
    }
}