package uba.fi.goodreads.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import uba.fi.goodreads.core.ui.GoodReadsAppState
import uba.fi.goodreads.presentation.add_book.navigation.addBookScreen
import uba.fi.goodreads.presentation.add_book.navigation.navigateToAddBook
import uba.fi.goodreads.presentation.answer_quiz.navigation.answerQuizScreen
import uba.fi.goodreads.presentation.answer_quiz.navigation.navigateToAnswerQuiz
import uba.fi.goodreads.presentation.book_info.navigation.BOOKINFO_ROUTE
import uba.fi.goodreads.presentation.book_info.navigation.bookInfoScreen
import uba.fi.goodreads.presentation.book_info.navigation.navigateToBookInfo
import uba.fi.goodreads.presentation.create_quiz.navigation.createQuizScreen
import uba.fi.goodreads.presentation.create_quiz.navigation.navigateToCreateQuiz
import uba.fi.goodreads.presentation.edit_book.navigation.editBookScreen
import uba.fi.goodreads.presentation.edit_book.navigation.navigateToEditBook
import uba.fi.goodreads.presentation.edit_profile.navigation.editProfileScreen
import uba.fi.goodreads.presentation.edit_profile.navigation.navigateToEditProfile
import uba.fi.goodreads.presentation.review.navigation.navigateToReviewScreen
import uba.fi.goodreads.presentation.home.navigation.HOME_ROUTE
import uba.fi.goodreads.presentation.home.navigation.homeScreen
import uba.fi.goodreads.presentation.profile.navigation.navigateToProfile
import uba.fi.goodreads.presentation.profile.navigation.profileScreen
import uba.fi.goodreads.presentation.shelves.shelf_books.navigation.navigateToShelfBooks
import uba.fi.goodreads.presentation.shelves.shelf_books.navigation.shelfBooksScreen
import uba.fi.goodreads.presentation.shelves.shelves.navigation.shelvesScreen
import uba.fi.goodreads.presentation.search.search.navigation.searchScreen
import uba.fi.goodreads.presentation.review.navigation.reviewScreen
import uba.fi.goodreads.presentation.search.genre.navigation.genreScreen
import uba.fi.goodreads.presentation.search.genre.navigation.navigateToGenre
import uba.fi.goodreads.presentation.shelves.add_book.navigation.addBookToShelvesScreen
import uba.fi.goodreads.presentation.shelves.add_book.navigation.navigateToAddBookToShelves

@Composable
fun GoodReadsNavHost(
    appState: GoodReadsAppState,
    modifier: Modifier = Modifier,
    startDestination: String = HOME_ROUTE,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen(
            navigateToBook = navController::navigateToBookInfo
        )

        shelvesScreen(
            navigateToShelfBooks = navController::navigateToShelfBooks
        )

        shelfBooksScreen(
            onBack = navController::popBackStack,
            onBookInfo = navController::navigateToBookInfo
        )

        bookInfoScreen(
            navigateToReview = navController::navigateToReviewScreen,
            navigateToAddBookToShelf = navController::navigateToAddBookToShelves,
            navigateToCreateQuiz = navController::navigateToCreateQuiz,
            navigateToAnswerQuiz = navController::navigateToAnswerQuiz,
            navigateToEditBook = navController::navigateToEditBook,
        )

        profileScreen(
            onBack = navController::popBackStack,
            onAddBook = navController::navigateToAddBook,
            navigateToEditProfile = navController::navigateToEditProfile
        )

        editProfileScreen(
            onBack = navController::popBackStack
        )

        searchScreen(
            navigateToBook = navController::navigateToBookInfo,
            navigateToProfile = navController::navigateToProfile,
            navigateToGenre = navController::navigateToGenre
        )

        reviewScreen(
            onBack = navController::popBackStack
        )

        addBookToShelvesScreen(
            onBack = navController::popBackStack
        )

        genreScreen(
            navigateToBook = navController::navigateToBookInfo
        )

        createQuizScreen(
            onBack = navController::popBackStack
        )

        answerQuizScreen (
            onBack = navController::popBackStack
        )


        addBookScreen(
            onBack = navController::popBackStack
        )

        editBookScreen(
            onBack = navController::popBackStack,
            navigateHomeAndClearStack = {
                navController.popBackStack(BOOKINFO_ROUTE, true)
            }
        )
    }
}
