package com.books.android.booksapp.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.books.android.booksapp.model.Book
import com.books.android.booksapp.typeconverters.StringListConverter
@Database(entities = [Book::class], version = 1, exportSchema = true)
@TypeConverters(StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao
}