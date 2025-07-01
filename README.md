# 🎬 BingeMate – Your AI-Powered Movie & Series Finder 🎙️📺

**BingeMate** is a Jetpack Compose Android application that helps users discover movies and series using **Voice input** and **Semantic search** powered by **MindsDB Knowledge Base** and **Ollama LLMs**.

It combines natural language queries with structured filters like genre, type, year, and IMDb rating to return personalized results from Mindsdb Knowledge base.
App's backend link is required to get movie/series data for Semantic/agent search - [BingeMate-Backend](https://github.com/anuragkanojiya1/BingeMate-Backend)

<br/>

| Demo Video                                                                 | Blog Post                                                                 |
|----------------------------------------------------------------------------|--------------------------------------------------------------------------|
| [![YouTube](https://github.com/user-attachments/assets/6cf3059f-84e8-4800-bade-11bd46a5f2e5)](https://youtu.be/QCIXBK0dxEs?si=rYhLqLZQ6KfP0onP) | [![Blog](https://github.com/user-attachments/assets/f4a138fc-9692-4486-bae4-6bb666ee3f72)](https://dev.to/anuragkanojiya/semantic-search-for-movies-series-with-mindsdb-and-fastapi-g39) |


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

## 📲 Android Studio Setup

### ✅ Prerequisites

- Android Studio **Giraffe** (or newer)
- Android SDK version **33+**
- Kotlin 1.9+
- JDK 17
- Emulator.

### 🔧 Setup Instructions

1. **Clone the repository**:

   ```bash
   git clone https://github.com/anuragkanojiya1/bingemate.git
   cd bingemate
   ```

2. Open in Android Studio:
- Launch Android Studio.
- Select "Open an existing project".
- Navigate to the cloned bingemate folder.

3. Sync Gradle:
- Let Android Studio download all dependencies.
- Accept any SDK or build tool prompts.

4. Run the App:
- Use an emulator.
- Click ▶️ "Run" to install and launch BingeMate.


---

## 🧑‍💻 Author
Anurag — Android app Developer | AI Enthusiast | Spring Boot

• [Twitter](https://x.com/AnuKanojiya829) • [LinkedIn](https://linkedin.com/in/anurag-kanojiya-101312286) • [GitHub](https://github.com/anuragkanojiya1)
