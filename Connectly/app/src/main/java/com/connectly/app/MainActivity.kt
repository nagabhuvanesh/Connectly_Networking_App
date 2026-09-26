package com.connectly.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.connectly.app.core.theme.ConnectlyTheme
import com.connectly.app.navigation.ConnectlyNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConnectlyTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ConnectlyNavGraph()
                }
            }
        }
    }
}
