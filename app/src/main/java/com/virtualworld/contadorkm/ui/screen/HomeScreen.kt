package com.virtualworld.contadorkm.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.maps.android.compose.GoogleMap

@Composable
fun HomeScreen(bottomPadding: Dp = 0.dp,
               navController: NavController){

    MyGoogleMaps()

}

@Composable
fun MyGoogleMaps()
{










    GoogleMap(modifier = Modifier.fillMaxSize())

}
