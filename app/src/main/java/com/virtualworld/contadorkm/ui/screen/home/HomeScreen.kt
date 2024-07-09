package com.virtualworld.contadorkm.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.virtualworld.contadorkm.core.data.model.Run
import com.virtualworld.contadorkm.navegation.DestinationApp
import com.virtualworld.contadorkm.ui.screen.home.component.CurrentRunningCard
import com.virtualworld.contadorkm.ui.screen.home.component.EmptyRunListView
import com.virtualworld.contadorkm.ui.screen.home.component.RecentRunList
import com.virtualworld.contadorkm.ui.screen.home.component.RunInfoDialog

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), bottomPadding: Dp = 0.dp, navController: NavController)
{

    val state by viewModel.homeScreenState.collectAsStateWithLifecycle()
    val durationInMillis by viewModel.durationInMillis.collectAsStateWithLifecycle()

    HomeScreenContent(bottomPadding = bottomPadding,
                      state = state,
                      durationInMillis = durationInMillis,
                      deleteRun = viewModel::deleteRun,
                      showRun = viewModel::showRun,
                      dismissDialog = viewModel::dismissRunDialog,
                      navigateToRunScreen = { DestinationApp.navigateToCurrentRunScreen(navController) })
}


@Composable
fun HomeScreenContent(
    bottomPadding: Dp = 0.dp,
    state: HomeScreenState,
    durationInMillis: Long,
    deleteRun: (Run) -> Unit,
    showRun: (Run) -> Unit,
    dismissDialog: () -> Unit,
    navigateToRunScreen: () -> Unit,
)
{
    Column {

        //card que puestra la carrera en proceso
        if (durationInMillis > 0) CurrentRunningCard(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 28.dp)
                .clickable(onClick = navigateToRunScreen),
            durationInMillis = durationInMillis,
            runState = state.currentRunStateUI,
        )

        //
        Row(modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(vertical = 28.dp, horizontal = 24.dp)) {
            Text(text = "Carreras",
                 style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                 modifier = Modifier.weight(1f))

        }

        //column de lista de carreras
        Column(modifier = Modifier
            .weight(1f)
            .verticalScroll(rememberScrollState())
            .padding(bottom = bottomPadding)) {
            if (state.runList.isEmpty()) EmptyRunListView(modifier = Modifier)
            else RecentRunList(runList = state.runList, onItemClick = showRun)
        }
    }
    state.currentRunInfo?.let {
        RunInfoDialog(run = it, onDismiss = dismissDialog, onDelete = deleteRun)
    }
}