package com.books.android.booksapp.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.serialization.Serializable
import javax.annotation.concurrent.Immutable
import com.books.android.booksapp.typeconverters.StringListConverter
@Serializable
@Entity
@Immutable
data class Book(
    @PrimaryKey val id: String,
    @Embedded val volumeInfo: VolumeInfo
) {
    companion object {

        fun mock() = Book(
            id = "0",
            volumeInfo = VolumeInfo(
                title = "Harry Potter",
                subtitle = "Harry Potter subtitle",
                description = "Harry Potter description",
                authors = listOf<String>("J. K. Rowling"),
                publisher = "Bloomsbury",
                publishedDate = "26 June 1997"
            )
        )
    }
}

@Serializable
data class VolumeInfo(
    val title: String,
    val subtitle: String,
    val description: String,
    @Embedded val imageLinks: ImageLinks? = null,
    @TypeConverters(StringListConverter::class) val authors: List<String>,
    val publisher: String,
    val publishedDate: String,
) {

    fun allAuthors() : String {
        var x= ""
        for (author in authors) {
            x += "$author, "
        }
        return x.trimEnd(',', ' ')
    }
}

@Serializable
data class ImageLinks(
    val smallThumbnail: String,
    val thumbnail: String,
) {
    val httpsThumbnail : String
        get() = thumbnail.replace("http", "https")
}
