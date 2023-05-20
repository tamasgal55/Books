package com.books.android.booksapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.books.android.booksapp.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
): ViewModel() {
    private val _bookList = MutableLiveData<List<Book>>()
    private val _storedBookList = MutableLiveData<List<Book>>()
    val bookList: LiveData<List<Book>> get() = _bookList
    val storedBooks: LiveData<List<Book>> get() = _storedBookList
    fun getBooks(query: String) {
        viewModelScope.launch {
            _bookList.value = mainRepository.getBooks(query)
        }
    }

    fun getStoredBooks() {
        viewModelScope.launch {
            _storedBookList.value = mainRepository.getStoredBooks()
        }
    }

    fun storeBook(book: Book) {
        viewModelScope.launch {
            mainRepository.storeBook(book)
        }
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch {
            mainRepository.deleteBook(book)
            getStoredBooks()
        }
    }

}