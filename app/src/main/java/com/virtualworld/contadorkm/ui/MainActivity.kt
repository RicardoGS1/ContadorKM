package com.virtualworld.contadorkm.ui


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.virtualworld.contadorkm.ui.screen.main.MainScreen
import com.virtualworld.contadorkm.ui.theme.ContadorKMTheme
import com.virtualworld.contadorkm.ui.utils.LocationPermissionRequestDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity()
{


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent {
            ContadorKMTheme {

                PermissionRequester()

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainScreen(rememberNavController())
                }
            }
        }
    }


    //Solisitar permisos de GPS en tiempo de ejecucion
    @Composable
    private fun PermissionRequester()
    {

        var showPermissionDeclinedRationale by rememberSaveable { mutableStateOf(false) }
        var showRationale by rememberSaveable { mutableStateOf(false) }


        val permissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions(), onResult = {
            it.forEach { (permission, isGranted) ->
                if (!isGranted && locationPermissions.contains(permission))
                {
                    showPermissionDeclinedRationale = true
                }
            }
        })


        LaunchedEffect(key1 = Unit) {
            when
            {
                hasLocationPermission() ->
                {
                    //Si el usuario ya ha concedido los permisos se sale del efecto.
                    return@LaunchedEffect
                }

                locationPermissions.any { shouldShowRequestPermissionRationale(it) } ->
                {
                    // Si el usuario ha rechazado los permisos en algún momento
                    showRationale = true

                }

                else ->
                {
                    //solisitar permisos por primra ves
                    permissionLauncher.launch(locationPermissions)
                }
            }
        }

        if (showPermissionDeclinedRationale)
            LocationPermissionRequestDialog(onDismissClick = {
                if (!hasLocationPermission()) finish()
                else showPermissionDeclinedRationale = false
            }, onOkClick = { openAppSetting() })


        if (showRationale) LocationPermissionRequestDialog(onDismissClick = ::finish, onOkClick = {
            showRationale = false
            permissionLauncher.launch(locationPermissions)
        })
    }
}



val locationPermissions = listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION).toTypedArray()


fun Context.hasLocationPermission() = locationPermissions.all {
    ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
}


fun Context.openAppSetting()
{
    println("ve a setting")
    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null)).also(::startActivity)
}



