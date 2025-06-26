package com.example.mindsdbkb.composables

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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.example.mindsdbkb.navigation.NavRoutes

@Composable
fun Test(){
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

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF121212)),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(140.dp)
                .padding(top = 16.dp)
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

        Spacer(modifier = Modifier.size(16.dp))
        Button(
            onClick = {
//                    agentButtonPressed = true
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White
            ),
            border = BorderStroke(1.dp, Color(0xFF8A2BE2))
        ) {
            Text("Ask Agent")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun testPreview(){
    Test()
}