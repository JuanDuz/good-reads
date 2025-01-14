package uba.fi.goodreads.presentation.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import uba.fi.goodreads.R
import uba.fi.goodreads.core.design_system.component.feedback.FeedbackScreen
import uba.fi.goodreads.core.design_system.component.feedback.FeedbackType
import uba.fi.goodreads.core.design_system.component.loading.Loading
import uba.fi.goodreads.core.design_system.theme.GoodReadsTheme
import uba.fi.goodreads.domain.model.Book
import uba.fi.goodreads.presentation.home.HomeScreenPreviewParameterProvider
import uba.fi.goodreads.presentation.home.HomeUiState
import uba.fi.goodreads.presentation.home.HomeViewModel
import uba.fi.goodreads.presentation.home.navigation.HomeDestination

@Composable
fun HomeRoute(
    navigate: (HomeDestination) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val screenState by viewModel.screenState.collectAsState()

    LaunchedEffect((screenState as? HomeUiState.Success)?.destination) {
        (screenState as? HomeUiState.Success)?.destination?.let { destination ->
            navigate(destination)
            viewModel.onClearDestination()
        }
    }

    HomeScreen(
        screenState = screenState,
        onBookClicked = viewModel::onBookClick
    )
}

@Composable
fun HomeScreen(
    screenState: HomeUiState,
    onBookClicked: (Int) -> Unit
) {
    val scrollState = rememberScrollState()
    when (screenState) {
        HomeUiState.Error -> FeedbackScreen(type = FeedbackType.ERROR)
        HomeUiState.Loading -> Loading()
        is HomeUiState.Success -> HomeScreenSuccessContent(
            screenState,
            onBookClicked,
        )
    }
}

@Composable
fun HomeScreenSuccessContent(
    screenState: HomeUiState.Success,
    onBookClicked: (Int) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(Modifier.height(32.dp))

        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = stringResource(id = R.string.home_for_you_section_title)
        )

        screenState.forYou.forEach { book ->
            BookRecommendation(
                book = book,
                onBookClicked = onBookClicked
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}


@Composable
private fun Post(
    title: String,
    content: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxSize(),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                Modifier
                    .size(32.dp)
                    .background(color = Color.Gray, shape = CircleShape)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = title)

                Text(text = content)
            }
        }
    }
}

@Composable
private fun BookRecommendation(
    book: Book,
    onBookClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onBookClicked(book.id.toInt()) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = book.title,
                textAlign = TextAlign.Center
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(book.photoUrl)
                    .crossfade(true)
                    .listener(
                        onError = { _, throwable ->
                            throwable.throwable.printStackTrace()
                        }
                    )
                    .build(),
                // placeholder = painterResource(R.drawable.placeholder),
                modifier = Modifier
                    .height(200.dp),
                contentDescription = book.description,
                contentScale = ContentScale.Fit,
            )

            Text(
                text = book.author,
                textAlign = TextAlign.Center,

                )
        }

    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(
    @PreviewParameter(HomeScreenPreviewParameterProvider::class) state: HomeUiState
) {
    GoodReadsTheme {
        HomeScreen(
            state,
            onBookClicked = {}
        )
    }
}