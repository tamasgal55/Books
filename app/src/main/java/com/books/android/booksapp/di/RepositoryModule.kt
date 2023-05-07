package com.books.android.booksapp.di

import com.books.android.booksapp.network.BooksService
import com.books.android.booksapp.ui.main.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideMainRepository(
        booksService: BooksService
    ): MainRepository {
        return MainRepository(booksService)
    }
}