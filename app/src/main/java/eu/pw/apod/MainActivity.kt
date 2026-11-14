package eu.pw.apod

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import eu.pw.apod.presentation.apod.ApodScreen
import eu.pw.designsystem.ui.theme.ApodTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			ApodTheme {
				Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
					ApodScreen(
						modifier = Modifier.padding(innerPadding),
					)
				}
			}
		}
	}
}