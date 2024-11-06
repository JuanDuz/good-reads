package uba.fi.goodreads.presentation.review.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import uba.fi.goodreads.presentation.review.ReviewViewModel
import uba.fi.goodreads.presentation.review.navigation.ReviewDestination

@Composable
fun ReviewRoute(
    navigate: (ReviewDestination) -> Unit,
    viewModel: ReviewViewModel = hiltViewModel(),
) {
    val screenState by viewModel.screenState.collectAsState()

    LaunchedEffect(screenState.destination) {
        screenState.destination?.let { destination ->
            navigate(destination)
            viewModel.onClearDestination()
        }
    }

    ReviewScreen(
        onBack = viewModel::onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    onBack: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(onBack = onBack)
        ReviewTextField()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onBack: () -> Unit) {
    TopAppBar(
        title = { Text("Write a review") },
        navigationIcon = {
            IconButton(
                onClick = onBack
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save Review"
                )
            }
        }
    )
}

@Composable
fun ReviewTextField() {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { newText -> text = newText },
        label = { Text("Write a review") },
        modifier = Modifier.fillMaxSize()
    )
}







@Composable
@Preview(showBackground = true)
fun ReviewsScreenPreview() {
    ReviewScreen({})
}