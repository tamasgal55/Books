package com.books.android.booksapp.ui.main

import android.view.KeyEvent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.books.android.booksapp.model.Book
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.books.R
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextAlign

@Composable
fun BooksMainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val bookList = viewModel.storedBooks.observeAsState()

    viewModel.getStoredBooks()
    val selectedBook = remember { mutableStateOf<Book?>(null) }
    LaunchedEffect(selectedBook.value) {
        val selectedBookValue = selectedBook.value
        if (selectedBookValue != null) {
            navController.navigate("Details/${selectedBookValue.id}")
        }
    }
    Column() {
        Text(
            text = "Books",
            modifier = Modifier.align(CenterHorizontally),
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold
        )
        Box(modifier = Modifier.fillMaxWidth().height(50.dp).padding(13.dp)) {
            IconButton(
                onClick = { navController.navigate("QueryScreen") },
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterEnd)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        }
        BooksList(books = bookList.value, queryScreen = false, selectedBook = selectedBook)
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QueryScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val bookList = viewModel.bookList.observeAsState()
    val focusManager = LocalFocusManager.current
    Column(Modifier.padding(16.dp)) {
        val textState = remember { mutableStateOf(TextFieldValue()) }
        OutlinedTextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            singleLine = true,
            placeholder = {
                Text(text = "Search")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focusManager.clearFocus()
                    viewModel.getBooks(textState.value.text)
                }
            ),
            modifier = Modifier
                .onKeyEvent { e ->
                    if (e.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                        focusManager.clearFocus()
                        viewModel.getBooks(textState.value.text)
                    }
                    false
                }
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        BooksList(books = bookList.value, queryScreen = true, null)
    }
}

@Composable
fun BooksList(books: List<Book>?, queryScreen: Boolean, selectedBook: MutableState<Book?>?) {
    LazyColumn() {
        if (books != null) {
            items(books.size) { index ->
                BookCard(books[index], queryScreen, selectedBook)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookCard(
    book: Book,
    queryScreen: Boolean,
    selectedBook: MutableState<Book?>?,
    viewModel: MainViewModel = hiltViewModel()
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        onClick = { if(!queryScreen) selectedBook?.value = book }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top // Hozzáadott módosítás
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(0.8f),
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(book.volumeInfo.imageLinks?.thumbnail)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    error = painterResource(id = R.drawable.ic_not_found),
                    placeholder = painterResource(id = R.drawable.ic_loading),
                    contentScale = ContentScale.FillBounds
                )
            }
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(8.dp)
                    .align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = book.volumeInfo.title.orEmpty(),
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                book.volumeInfo.description?.let { description ->
                    Text(
                        text = if (description.length <= 50) description else description.substring(0, 50) + "...",
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Start
                    )
                }
                if (queryScreen) {
                    IconButton(
                        onClick = { viewModel.storeBook(book) },
                        modifier = Modifier
                            .wrapContentWidth()
                            .height(IntrinsicSize.Min)
                            .align(Alignment.End)
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Add")
                    }
                } else {
                    IconButton(
                        onClick = { viewModel.deleteBook(book) },
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.End)
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
    }
}





