package com.example.mindsdbkb

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mindsdbkb.composables.AgentScreen
import com.example.mindsdbkb.composables.MovieScreen
import com.example.mindsdbkb.navigation.NavRoutes
import com.example.mindsdbkb.ui.theme.MindsDBKBTheme
import com.example.mindsdbkb.viewmodel.BingeViewModel
import java.util.Locale

class MainActivity : ComponentActivity() {

    var speechInput = mutableStateOf("")

    lateinit var bingeViewModel: BingeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val viewModel = viewModel<BingeViewModel>()

            NavHost(navController = navController, startDestination = NavRoutes.MovieScreen) {
                composable(NavRoutes.MovieScreen) {
                    MovieScreen(viewModel = viewModel, navController = navController)
                }
                composable(NavRoutes.AgentScreen) {
                    AgentScreen(viewModel = viewModel) {
                        navController.popBackStack()
                    }
                }
            }
        }
    }

    fun askSpeechInput(context: Context) {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            Toast.makeText(context, "Speech not Available", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Talk")
            Log.d("MainActivity", "Launching speech recognition")
            startActivityForResult(intent, 102)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 102 && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            speechInput.value = result?.get(0).toString()
            Log.d("MainActivity", "Speech recognition result: '${speechInput.value}'")
        } else if (requestCode == 102) {
            Log.d("MainActivity", "Speech recognition failed or was cancelled. Result code: $resultCode")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MindsDBKBTheme {
        Greeting("Android")
    }
}