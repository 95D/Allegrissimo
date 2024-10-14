package com.viento.allegrissimo.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.viento.allegrissimo.tempo.ui.TempoRoute
import com.viento.allegrissimo.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

const val TEMPO_SCREEN = "tempo_screen"

@Composable
@Preview
fun App() {
    AppTheme {
        Scaffold(
            topBar = { AppBar() }
        ) { innerPadding ->
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                color = MaterialTheme.colorScheme.background,
            ) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = TEMPO_SCREEN
                ) {
                    composable(TEMPO_SCREEN) {
                        TempoRoute()
                    }
                }
            }
        }
    }
}

// TopAppBar is marked as experimental in Material 3
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TopAppBar(
            title = { Text("Allegrissimo") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Unspecified,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
        )
    }
}