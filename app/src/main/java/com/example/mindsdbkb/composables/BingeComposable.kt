package com.example.mindsdbkb.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.mindsdbkb.model.MetadataResponse
import com.example.mindsdbkb.model.Result

@Composable
fun BingeComposable(searchResults: List<Result>) {
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        items(searchResults) { result ->
    Column { searchResults.forEach { result ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1E1E1E)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = result.id,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = result.chunk_content,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "IMDb Rating: ${result.metadata.IMDb_Rating}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFFFD700) // Gold-ish rating color
                    )
                    Text(
                        text = "Type: ${result.metadata.Type}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.LightGray
                    )
                    Text(
                        text = "Year: ${result.metadata.Year}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.LightGray
                    )
                    Text(
                        text = "Genre: ${result.metadata.Genre}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.LightGray
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@PreviewLightDark
@Composable
fun BingeComposablePreview() {
    val mockResults = listOf(
        Result(
            id = "1",
            chunk_content = "A thrilling sci-fi movie set in the future.",
            metadata = MetadataResponse(
                IMDb_Rating = "8.5",
                Type = "Movie",
                Year = "2023",
                Genre = "Sci-Fi, Action"
            ),
            relevance = 0.9
        ),
        Result(
            id = "2",
            chunk_content = "A dramatic tale of love and loss.",
            metadata = MetadataResponse(
                IMDb_Rating = "7.9",
                Type = "Movie",
                Year = "2021",
                Genre = "Drama, Romance"
            ),
            relevance = 0.85
        )
    )

    MaterialTheme {
        BingeComposable(mockResults)
    }
}
