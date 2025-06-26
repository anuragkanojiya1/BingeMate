package com.example.mindsdbkb.model

data class Result(
    val chunk_content: String,
    val id: String,
    val metadata: MetadataResponse,
    val relevance: Double
)