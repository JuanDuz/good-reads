package uba.fi.goodreads.presentation.shelves.shelfBooks.composables

import uba.fi.goodreads.domain.model.Book
import uba.fi.goodreads.presentation.shelves.shelfBooks.ShelfBooksUiState
import uba.fi.goodreads.presentation.shelves.shelfBooks.ShelfBooksViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import uba.fi.goodreads.R
import uba.fi.goodreads.core.design_system.component.feedback.FeedbackScreen
import uba.fi.goodreads.core.design_system.component.feedback.FeedbackType
import uba.fi.goodreads.core.design_system.component.loading.Loading
import uba.fi.goodreads.core.design_system.theme.GoodReadsTheme
import uba.fi.goodreads.presentation.shelves.shelfBooks.ShelfBooksPreviewParameterProvider
import uba.fi.goodreads.presentation.shelves.shelvesScreen.ShelvesScreenPreviewParameterProvider
import uba.fi.goodreads.presentation.shelves.shelvesScreen.ShelvesUiState

@Composable
fun ShelfBooksRoute(
    viewModel: ShelfBooksViewModel = hiltViewModel(),
) {
    val screenState by viewModel.screenState.collectAsState()

    ShelfBooksScreen(
        screenState = screenState
    )
}

@Composable
fun ShelfBooksScreen(
    screenState: ShelfBooksUiState
) {
    when (screenState) {
        ShelfBooksUiState.Error -> FeedbackScreen(type = FeedbackType.ERROR)
        ShelfBooksUiState.Loading -> Loading()
        is ShelfBooksUiState.Success -> SuccessContent(
            screenState
        )
    }
}

@Composable
private fun SuccessContent(
    screenState: ShelfBooksUiState.Success
) {

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(vertical = 32.dp),
            text = stringResource(id = R.string.my_books_bottom_nav_title),
            fontSize = 32.sp
        )
        screenState.books.forEach { book ->
            BookSummary(book)
            Spacer(Modifier.height(16.dp))
        }
        Spacer(Modifier.weight(1f))

    }
}

@Composable
private fun BookSummary(book: Book) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://f.media-amazon.com/images/I/41tjPqycZ1L._SY445_SX342_.jpg") // TODO AL MODEL
                    .crossfade(true)
                    .build(),
                // placeholder = painterResource(R.drawable.placeholder),
                contentDescription = "Cover of one of the books present inside the shelf",
                contentScale = ContentScale.Crop,
            )
            Column {
                Text(
                    text = book.title,
                )
                Text(
                    text = "from " + book.author
                )
                Text(
                    text = "average rating: " + book.avgRating
                )
                Text(
                    text = "original publication date " + book.publicationDate
                )
            }


        }
    }
}


@Composable
@Preview(showBackground = true)
fun ShelvesScreenPreview(
    @PreviewParameter(ShelfBooksPreviewParameterProvider::class) state: ShelfBooksUiState
) {
    GoodReadsTheme {
        ShelfBooksScreen(screenState = state
        )
    }
}