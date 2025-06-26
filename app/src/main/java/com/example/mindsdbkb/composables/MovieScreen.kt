@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mindsdbkb.composables

import android.util.Log
import android.widget.Button
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.mindsdbkb.MainActivity
import com.example.mindsdbkb.navigation.NavRoutes
import com.example.mindsdbkb.viewmodel.BingeViewModel
import kotlinx.coroutines.launch

// Data Models
data class RecentSearch(val title: String, val timeAgo: String, val posterUrl: String)
data class Movie(val title: String, val year: String, val rating: String, val posterUrl: String)
data class FilterChip(val label: String, val key: String)

@Composable
fun MovieScreen(viewModel: BingeViewModel, navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    var query by remember { mutableStateOf("") }
    val categories = listOf("Popular Now", "Oscar Winners", "New Releases")
    var selectedCategory by remember { mutableStateOf(0) }

    val searchResults = viewModel.bingeList.collectAsState()
    val metadata = remember { mutableStateMapOf<String, String>() }

    val agentResponse by viewModel.agentResponse.collectAsState()
    var agentQuery by remember { mutableStateOf("") }

    // Chips state
    val filterChips = listOf(
        FilterChip("Genre", "Genre"),
        FilterChip("Type", "Type"),
        FilterChip("Year", "Year"),
        FilterChip("IMDb Rating", "Rating")
    )

    var selectedFilter by remember { mutableStateOf<String?>(null) }
    var metaDataText by remember { mutableStateOf("") }

    var agentButtonPressed by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val speechContext = context as MainActivity

    val recentSearches = listOf(
        RecentSearch("Inception", "2 hours ago", "https://image.tmdb.org/t/p/w500/qmDpIHrmpJINaRKAfWQfftjCdyi.jpg"),
        RecentSearch("The Dark Knight", "Yesterday", "https://image.tmdb.org/t/p/w500/1hRoyzDtpgMU7Dz4JF22RANzQO7.jpg"),
        RecentSearch("Stranger Things", "2 days ago", "https://image.tmdb.org/t/p/w500/x2LSRK2Cm7MZhjluni1msVJ3wDF.jpg")
    )

    val trending = listOf(
        Movie("Dune", "2021", "8.0", "https://image.tmdb.org/t/p/w500/d5NXSklXo0qyIYkgV94XAgMIckC.jpg"),
        Movie("The Matrix", "1999", "8.7", "https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg"),
        Movie("Interstellar", "2014", "8.6", "https://image.tmdb.org/t/p/w500/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg")
    )

    val infiniteTransition = rememberInfiniteTransition()

    val animatedRadius by infiniteTransition.animateFloat(
        initialValue = 120f,
        targetValue = 200f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "radius"
    )

    val animatedOffset2 by infiniteTransition.animateValue(
        initialValue = Offset(180f, 220f),
        targetValue = Offset(220f, 180f),
        typeConverter = TwoWayConverter(
            convertToVector = { AnimationVector2D(it.x, it.y) },
            convertFromVector = { Offset(it.v1, it.v2) }
        ),
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offset2"
    )

    LaunchedEffect(speechContext.speechInput.value) {
        if (speechContext.speechInput.value.isNotBlank()) {
            agentQuery = speechContext.speechInput.value
            speechContext.speechInput.value = ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Spacer(Modifier.height(36.dp))
        Text("Hey, I'm BingeMate", color = Color.White, style = MaterialTheme.typography.headlineMedium)
        Text("Your AI movie/series companion", color = Color.LightGray, style = MaterialTheme.typography.bodyMedium)

        Spacer(Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .size(140.dp)
                .align(Alignment.CenterHorizontally)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFF8A2BE2), Color.Transparent),
                        center = animatedOffset2,
                        radius = animatedRadius,
                        tileMode = TileMode.Clamp
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center,
        ) {
            IconButton(
                onClick = {
                    speechContext.askSpeechInput(context)
                },
                modifier = Modifier.size(120.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Speak",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        if (!agentButtonPressed) {
            Button(
                onClick = {
//                    agentButtonPressed = true
                    navController.navigate(NavRoutes.AgentScreen)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                ),
                border = BorderStroke(1.dp, Color(0xFF8A2BE2))
            ) {
                Text("Ask Agent")
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

//        if (agentButtonPressed){
//            OutlinedTextField(
//                value = agentQuery,
//                onValueChange = {
//                    agentQuery = it
//                },
//                label = { Text("Enter Query", color = Color.Gray) },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true,
//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    unfocusedTextColor = Color.Gray,
//                    unfocusedBorderColor = Color(0xFFE7E6E6),
//                    focusedBorderColor = Color(0xFF8A2BE2),
//                    focusedTextColor = Color.White
//                ),
//                shape = RoundedCornerShape(12.dp)
//            )
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        if (agentButtonPressed) {
//            Button(
//                onClick = {
//                    coroutineScope.launch {
//                        viewModel.getAgentResponse(agentQuery)
//                    }
//                },
//                colors = ButtonDefaults.outlinedButtonColors(
//                    contentColor = Color.White,
//                ),
//                border = BorderStroke(1.dp, Color(0xFF8A2BE2)),
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            ) {
//                Text("Search")
//            }
//        }
//
//        if (agentResponse.isNotEmpty()){
//            Text(agentResponse, color = Color.White)
//        }

//        Spacer(Modifier.height(24.dp))
//        Text("Hey, I'm MovieMind", color = Color.White, style = MaterialTheme.typography.headlineMedium)
//        Text("Your AI movie companion", color = Color.LightGray, style = MaterialTheme.typography.bodyMedium)

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            placeholder = { Text("Get list of movies and series…") },
//            colors = TextFieldDefaults.textFieldColors(cursorColor = Color.White),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedTextColor = Color.Gray,
                unfocusedBorderColor = Color(0xFFE7E6E6),
                focusedBorderColor = Color(0xFF8A2BE2),
                focusedTextColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(filterChips.size) { index ->
                val chip = filterChips[index]
                val isSelected = selectedFilter == chip.key

                Button(
                    onClick = { selectedFilter = chip.key },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) Color.White else Color(0xFF1F1F1F),
                        contentColor = if (isSelected) Color.Black else Color.White
                    ),
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(chip.label)
                }
            }

        }

        if (selectedFilter != null) {
            Spacer(modifier = Modifier.height(8.dp))
            MetadataSelector(selectedFilter = selectedFilter!!) { selectedValue ->
                metadata[selectedFilter!!] = selectedValue
                selectedFilter = null
                metaDataText = ""
            }
//            Row(verticalAlignment = Alignment.CenterVertically) {

//                    OutlinedTextField(
//                        value = metaDataText,
//                        onValueChange = {
//                            metaDataText = it
//
//                            Log.d("MetaData", metadata.toString())
//
//                        },
//                        label = { Text("Enter $selectedFilter", color = Color.Gray) },
//                        modifier = Modifier.fillMaxWidth(0.5f),
//                        singleLine = true,
//                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                            unfocusedTextColor = Color.Gray,
//                            unfocusedBorderColor = Color(0xFFE7E6E6),
//                            focusedBorderColor = Color(0xFF8A2BE2),
//                            focusedTextColor = Color.White
//                        ),
//                        shape = RoundedCornerShape(12.dp)
//                    )
//            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (metadata.isNotEmpty()) {
            SelectedMetadataChips(metadata = metadata) { keyToRemove ->
                metadata.remove(keyToRemove)
            }
        }

        OutlinedButton(
                onClick = {
                    coroutineScope.launch {
                        selectedFilter?.let { key ->
                            metadata[key] = metaDataText
                            metaDataText = ""
                            selectedFilter = null
                        }
                        viewModel.getList(query, min_relevance = 0.25f, metadata)
                    }
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                ),
                border = BorderStroke(1.dp, Color(0xFF8A2BE2)),
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text("Search")
            }


        Spacer(modifier = Modifier.height(4.dp))

        if (searchResults.value.isNotEmpty()) {
            BingeComposable(searchResults.value)
        }

        Spacer(Modifier.height(24.dp))
        Text("Popular Searches", color = Color.White, style = MaterialTheme.typography.bodySmall)
        Column {
            recentSearches.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    AsyncImage(
                        model = item.posterUrl,
                        contentDescription = item.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text(item.title, color = Color.White)
                        Text(item.timeAgo, color = Color.Gray, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))
        Text("Trending Now", color = Color.White, style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(16.dp))

        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            trending.forEach { movie ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .width(140.dp)
                        .padding(end = 12.dp)
                        .clickable { /* handle click */ }
                        .background(Color.Black)
                ) {
                    Column(Modifier.background(Color.Black)) {
                        AsyncImage(
                            model = movie.posterUrl,
                            contentDescription = movie.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            movie.title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        Text(
                            "${movie.year}   ★ ${movie.rating}",
                            color = Color.LightGray,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MovieScreenPreview() {
    MovieScreen(viewModel = BingeViewModel(), navController = rememberNavController())
}
