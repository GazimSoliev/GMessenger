package com.gazim.gmessenger.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gmessenger.app.presentation.generated.resources.Icon
import gmessenger.app.presentation.generated.resources.Res
import gmessenger.app.presentation.generated.resources.app_name
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SplashComposition() {
    val appName = stringResource(Res.string.app_name)
    val icon = painterResource(Res.drawable.Icon)
    Scaffold { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(icon, appName, modifier = Modifier.size(256.dp))
                Spacer(Modifier.height(64.dp))
                LinearProgressIndicator()
            }
        }
    }
}