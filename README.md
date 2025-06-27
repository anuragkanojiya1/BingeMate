# 🎬 BingeMate – Your AI-Powered Movie & Series Finder 🎙️📺

**BingeMate** is a Jetpack Compose Android application that helps users discover movies and series using **Voice input** and **Semantic search** powered by **MindsDB Knowledge Base** and **Ollama LLMs**.

It combines natural language queries with structured filters like genre, type, year, and IMDb rating to return personalized results from Mindsdb Knowledge base.
App's backend link is required to get movie/series data for Semantic/agent search - [BingeMate-Backend](https://github.com/anuragkanojiya1/BingeMate-Backend)

---

## 🚀 Features

### 🎤 Voice-Powered Search
- Uses **Google Speech Services** for hands-free querying.

### 🔍 Semantic Movie/Series Search
- Queries are processed through **MindsDB's Knowledge Base**, which semantically matches user intent to documents (movie/series data) in the database.

### 📚 Metadata Filters
- Users can refine results using:
  - 🎭 `genre` (e.g., action, comedy)
  - 🎞️ `type` (movie/series)
  - 📅 `year` (e.g., 2023)
  - ⭐ `IMDb rating` (e.g., >8.0)

### 🤖 Conversational Agent
- A built-in **agent powered by Ollama (Mistral)** responds to natural questions like:
  - "What are the top-rated sci-fi movies of 2023?"
  - "Tell me something funny to watch tonight"

---

## 🛠️ Tech Stack

### 📱 Android App
- Jetpack Compose
- Kotlin
- Google Speech Services

### 🧠 [Backend](https://github.com/anuragkanojiya1/BingeMate-Backend) (Preconfigured)
- FastAPI for API routing
- MindsDB + Ollama for Semantic search & LLM agent
- ChromaDB for KB data storage

---

