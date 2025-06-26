package com.example.mindsdbkb.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MetadataSelector(
    selectedFilter: String,
    onValueSelected: (String) -> Unit
) {
    val options = when (selectedFilter) {
        "Genre" -> listOf("Action", "Drama", "Comedy", "Thriller", "Romance", "Horror", "Sci-Fi")
        "Type" -> listOf("Movie", "Series", "Documentary", "Anime")
        "Year" -> listOf("2023", "2022", "2021", "2010", "2000", "1990")
        "Rating" -> listOf("9", "8", "7", "6")
        else -> emptyList()
    }

    LazyRow {
        items(options.size) { index ->
            val value = options[index]
            AssistChip(
                onClick = { onValueSelected(value) },
                label = { Text(value) },
                modifier = Modifier.padding(end = 8.dp),
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = Color.DarkGray,
                    labelColor = Color.White
                )
            )
        }
    }
}

@Composable
fun SelectedMetadataChips(
    metadata: SnapshotStateMap<String, String>,
    onRemove: (String) -> Unit
) {
    Column {
        metadata.forEach { (key, value) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 0.dp)
            ) {
                AssistChip(
                    onClick = { },
                    label = { Text("$key: $value") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove",
                            modifier = Modifier.clickable { onRemove(key) }
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = Color(0xFF3D3D3D),
                        labelColor = Color.White
                    )
                )
            }
        }
    }
}
