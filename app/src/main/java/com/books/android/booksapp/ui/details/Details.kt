package com.books.android.booksapp.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Details (
    bookId: String,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    viewModel.getBookById(bookId)
    val book = viewModel.book.observeAsState()
    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        book.value?.volumeInfo?.title?.let {
            Row( modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(
                    text = it,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 10.dp, bottom = 5.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Text(
            text = "Author(s): " + book.value?.volumeInfo?.allAuthors(),
            modifier = Modifier.padding(top = 25.dp, bottom = 5.dp),
            fontSize = 18.sp,
        )
        
        Text(
            text = "Number of pages: " + book.value?.volumeInfo?.pageCount,
            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
            fontSize = 18.sp,
        )
        Text(
            text = "Published on: " + book.value?.volumeInfo?.publishedDate,
            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
            fontSize = 18.sp,
        )
        book.value?.volumeInfo?.description?.let { Text(text = it, fontSize = 18.sp, modifier = Modifier.padding(top = 10.dp, bottom = 5.dp),) }
    }

}