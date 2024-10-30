package uba.fi.goodreads.data.users.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import uba.fi.goodreads.data.books.response.BookNetworkDto
import uba.fi.goodreads.data.shelf.response.ShelfNetworkDto
import uba.fi.goodreads.domain.model.User

@Serializable
data class UserNetworkDto(
    @SerialName("name") val name: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("email") val email: String,
    @SerialName("id") val id: Int,
    @SerialName("shelves") val shelves: List<ShelfNetworkDto>,
    @SerialName("rated_books") val ratedBooks: List<BookNetworkDto>,
    @SerialName("following") val following: List<UserNetworkDto>,
    @SerialName("followers") val followers: List<UserNetworkDto>,
) {
    fun toDomain() = User(
        name = name,
        lastName = lastName,
        email = email,
        id = id,
        shelves = shelves.map { it.toDomain() },
        ratedBooks = ratedBooks.map { it.toDomain() },
        following = following.size,
        followers = followers.size,
    )
}