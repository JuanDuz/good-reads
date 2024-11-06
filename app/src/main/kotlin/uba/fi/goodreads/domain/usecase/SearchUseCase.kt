package uba.fi.goodreads.domain.usecase

import uba.fi.goodreads.core.network.NetworkResult
import uba.fi.goodreads.data.books.repositories.BooksRepository
import uba.fi.goodreads.data.users.repositories.UsersRepository
import uba.fi.goodreads.domain.model.Book
import uba.fi.goodreads.domain.model.User
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val booksRepository: BooksRepository,
    private val usersRepository: UsersRepository
) {
    sealed class Result {
        data class Success(val books: List<Book>, val users: List<User>) : Result()
        data class Error(val title: String? = null, val description: String? = null) : Result()
        data object UnexpectedError : Result()
    }

    suspend operator fun invoke(text: String): Result {
        return when (val booksResult = booksRepository.getBooks(text)
        ) {
            is NetworkResult.ErrorBase,
            is NetworkResult.LocalError,
            is NetworkResult.NetworkError -> Result.UnexpectedError
            is NetworkResult.Success -> {
                val usersResult = usersRepository.searchUsers(text)
                if (usersResult is NetworkResult.Success) {
                    Result.Success(books = booksResult.value, users = usersResult.value)
                } else {
                    Result.Success(books = booksResult.value, users = emptyList())
                }
            }
        }
    }
}