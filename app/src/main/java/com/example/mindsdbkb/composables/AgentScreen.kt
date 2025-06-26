package com.example.mindsdbkb.composables

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.widget.Toast
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindsdbkb.MainActivity
import com.example.mindsdbkb.viewmodel.BingeViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgentScreen(viewModel: BingeViewModel, onBack: () -> Unit){

    val coroutineScope = rememberCoroutineScope()
    val agentResponse by viewModel.agentResponse.collectAsState()

    var agentQuery = remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var styledText by remember { mutableStateOf<SpannableString?>(null) }

    val context = LocalContext.current

    val speechContext = context as MainActivity

    val infiniteTransition = rememberInfiniteTransition()
//    val animatedRadius by infiniteTransition.animateFloat(
//        initialValue = 180f,
//        targetValue = 240f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(durationMillis = 1200, easing = LinearEasing),
//            repeatMode = RepeatMode.Reverse
//        )
//    )

    LaunchedEffect(speechContext.speechInput.value) {
        if (speechContext.speechInput.value.isNotBlank()) {
            agentQuery.value = speechContext.speechInput.value
            speechContext.speechInput.value = ""
        }
    }

    // Animate radius for pulse
    val animatedRadius by infiniteTransition.animateFloat(
        initialValue = 120f,
        targetValue = 200f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "radius"
    )

    // Top animation
    val animatedOffset1 by infiniteTransition.animateValue(
        initialValue = Offset(180f, 360f),
        targetValue = Offset(360f, 180f),
        typeConverter = TwoWayConverter(
            convertToVector = { AnimationVector2D(it.x, it.y) },
            convertFromVector = { Offset(it.v1, it.v2) }
        ),
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offset1"
    )

    // Bottom animation (opposite direction)
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

    LaunchedEffect(agentResponse) {
        if (agentResponse.isNotEmpty()) {
            styledText = parseMarkdownStyle(agentResponse)
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .verticalScroll(rememberScrollState())
//            .padding(16.dp)
    ) {
        Spacer(Modifier.height(28.dp))

        IconButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.Start)
                .padding(top = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Spacer(Modifier.height(12.dp))
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Hey, I'm BingeMate",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                "Your AI movie/series companion",
                color = Color.LightGray,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .size(148.dp)
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
                IconButton(onClick = {
                    speechContext.askSpeechInput(context)
                },
                    modifier = Modifier.size(120.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Speak",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = agentQuery.value,
                onValueChange = {
                    agentQuery.value = it
                },
                label = { Text("Enter Query", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedTextColor = Color.Gray,
                    unfocusedBorderColor = Color(0xFFE7E6E6),
                    focusedBorderColor = Color(0xFF8A2BE2),
                    focusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (isLoading) {
                // Loading indicator
                Box(modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                    contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
            } else {
                // Search Button
                Button(
                    onClick = {
                        if (agentQuery.value.isNotEmpty()) {
                            isLoading = true
                            coroutineScope.launch {
                                viewModel.getAgentResponse(agentQuery.value)
                            }
                        } else {
                            Toast.makeText(context, "Please enter a query", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFF8A2BE2)),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Search")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (agentResponse.isNotEmpty()) {

                Text(styledText.toString(), color = Color.White,
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic)
            }

            Spacer(modifier = Modifier.height(24.dp))

        }
    }
    Spacer(modifier = Modifier.height(8.dp))

}


fun parseMarkdownStyle(input: String): SpannableString {
    val spannable = SpannableString(input)

    // Handle **bold**
    val boldRegex = Regex("""\*\*(.+?)\*\*""")
    boldRegex.findAll(input).forEach {
        val start = it.range.first
        val end = it.range.last + 1
        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            start,
            end - 3, // remove trailing **
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    // Remove ** from bold spans
    val cleanedBold = spannable.toString().replace(boldRegex, "$1")
    val cleanedSpannable = SpannableString(cleanedBold)

    // Handle *italic*
    val italicRegex = Regex("""\*(?!\*)(.+?)\*""")
    italicRegex.findAll(cleanedBold).forEach {
        val start = it.range.first
        val end = it.range.last + 1
        cleanedSpannable.setSpan(
            StyleSpan(Typeface.ITALIC),
            start,
            end - 1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    // Remove * from italic spans
    return SpannableString(cleanedSpannable.toString().replace(italicRegex, "$1"))
}

@Composable
@PreviewLightDark
@Preview(showBackground = true)
fun AgentScreenPreview(){
    AgentScreen(viewModel = BingeViewModel(), onBack = {})
}
