package com.virtualworld.contadorkm.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberMultiplePermissionsState

import com.virtualworld.contadorkm.RunUtils

import com.virtualworld.contadorkm.core.location.LocationUtils


import com.virtualworld.contadorkm.ui.theme.ContadorKMTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent {
            ContadorKMTheme {

                PermissionRequester()
                // A surface container using the 'background' color from the theme
                // Surface(
                //     modifier = Modifier.fillMaxSize(),
                //     color = MaterialTheme.colorScheme.background
                //  ) {
                //      MainScreen(rememberNavController())
                //  }
            }
        }
    }



    @Composable
    private fun PermissionRequester() {

        var showPermissionDeclinedRationale by rememberSaveable { mutableStateOf(false) }
        var showRationale by rememberSaveable { mutableStateOf(false) }

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = {
                it.forEach { (permission, isGranted) ->
                    if (!isGranted && locationPermissions.contains(permission)) {
                        showPermissionDeclinedRationale = true
                    }
                }
            }
        )

        if (showPermissionDeclinedRationale)
            LocationPermissionRequestDialog(
                onDismissClick = {
                    if (!hasLocationPermission())
                        finish()
                    else showPermissionDeclinedRationale = false
                },
                onOkClick = { openAppSetting() }
            )
        if (showRationale)
            LocationPermissionRequestDialog(
                onDismissClick = ::finish,
                onOkClick = {
                    showRationale = false
                    permissionLauncher.launch(allPermissions)
                }
            )
        LaunchedEffect(key1 = Unit) {
            when {
                hasAllPermission() -> return@LaunchedEffect
                locationPermissions.any { shouldShowRequestPermissionRationale(it) } -> showRationale =
                    true

                else -> permissionLauncher.launch(allPermissions)
            }
        }
    }

}

@Composable
fun LocationPermissionRequestDialog(
    modifier: Modifier = Modifier,
    onDismissClick: () -> Unit,
    onOkClick: () -> Unit,
) {

    AlertDialog(
        onDismissRequest = onDismissClick,
        title = {
            Text(
                text = "Permission Required",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Text(
                text = "LocationPoint permission is needed to record you running status.",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        modifier = modifier,
        confirmButton = {
            Button(
                onClick = onOkClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            ) {
                Text(
                    text = "Grant",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                )
            }
        },
        dismissButton = {
            Button(
                onClick = onDismissClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Text(
                    text = "Deny",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                )
            }
        },
    )

}



val locationPermissions = listOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
).toTypedArray()



val allPermissions = mutableListOf<String>().apply {
    addAll(locationPermissions)
}.toTypedArray()



fun Context.hasLocationPermission() =
    locationPermissions.all {
        ContextCompat.checkSelfPermission(
            this,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

fun Context.hasAllPermission() =
    allPermissions.all {
        ContextCompat.checkSelfPermission(
            this,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

fun Context.openAppSetting() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}
