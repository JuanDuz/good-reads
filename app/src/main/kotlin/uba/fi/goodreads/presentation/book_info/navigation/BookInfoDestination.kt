package uba.fi.goodreads.presentation.book_info.navigation

sealed class BookInfoDestination {
    data class Review(val bookId: String) : BookInfoDestination()
    data class AddBookToShelf(val bookId: String) : BookInfoDestination()
}