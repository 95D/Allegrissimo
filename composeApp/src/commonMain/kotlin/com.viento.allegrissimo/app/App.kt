package com.viento.allegrissimo.app

import allegrissimo.composeapp.generated.resources.Res
import allegrissimo.composeapp.generated.resources.hello_compose
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mohamedrejeb.calf.permissions.ExperimentalPermissionsApi
import com.viento.allegrissimo.tempo.ui.TempoScreen
import com.viento.allegrissimo.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource
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
                        TempoScreen()
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
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
        )
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer)
        ) {

            Text(
                text = stringResource(Res.string.hello_compose),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}