package com.books.android.booksapp.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.books.android.booksapp.model.Book

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookList(posters: List<Book>)

    @Query("SELECT * FROM Book WHERE id = :id_")
    suspend fun getBook(id_: String): Book?

    @Query("SELECT * FROM Book")
    suspend fun getBookList(): List<Book>

    @Insert
    suspend fun insert(book: Book)

    @Delete
    suspend fun delete(book: Book)
}