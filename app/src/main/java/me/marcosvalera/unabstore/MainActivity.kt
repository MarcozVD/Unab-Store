package me.marcosvalera.unabstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import me.marcosvalera.unabstore.ui.theme.UnabStoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnabStoreTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    HomeScreen(

                    )
                }
            }
        }
    }
}
