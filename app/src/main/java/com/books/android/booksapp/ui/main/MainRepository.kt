package com.books.android.booksapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.books.android.booksapp.model.Book
import com.books.android.booksapp.network.BooksService
import com.books.android.booksapp.persistence.BookDao
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val booksService: BooksService,
    private val bookDao: BookDao
) {
    suspend fun getBooks(query: String): List<Book>? {
        return try {
            val res = booksService.getBooks(query)
            if (res.isSuccessful) {
                res.body()?.items ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun storeBook(book: Book) {
        bookDao.insert(book)
    }

    suspend fun deleteBook(book: Book) {
        bookDao.delete(book)
    }

    suspend fun getStoredBooks(): List<Book>? {
        return bookDao.getBookList()
    }

}