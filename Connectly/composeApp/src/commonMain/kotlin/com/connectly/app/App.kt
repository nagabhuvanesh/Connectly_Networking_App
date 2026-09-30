package com.connectly.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import com.connectly.app.core.theme.ConnectlyTheme
import com.connectly.app.navigation.ConnectlyNavGraph

@Composable
fun App() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components { add(KtorNetworkFetcherFactory()) }
            .build()
    }

    ConnectlyTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ConnectlyNavGraph()
        }
    }
}
