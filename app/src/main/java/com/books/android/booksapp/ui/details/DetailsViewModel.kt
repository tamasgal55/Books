package com.books.android.booksapp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.books.android.booksapp.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository
) : ViewModel(){
    private val _book = MutableLiveData<Book?>()
    val book: LiveData<Book?> get() = _book

    fun getBookById(id: String) {
        viewModelScope.launch {
            _book.value = detailsRepository.getBookById(id)
        }
    }

}