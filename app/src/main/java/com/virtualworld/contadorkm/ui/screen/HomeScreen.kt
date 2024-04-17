package com.virtualworld.contadorkm.ui.screen

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.maps.android.compose.GoogleMap

import com.virtualworld.contadorkm.RunUtils
import com.virtualworld.contadorkm.core.location.LocationUtils


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
