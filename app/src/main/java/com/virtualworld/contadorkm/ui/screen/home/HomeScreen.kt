package com.virtualworld.contadorkm.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.virtualworld.contadorkm.R
import com.virtualworld.contadorkm.core.data.model.Run
import com.virtualworld.contadorkm.domain.model.CurrentRunStateWithCalories
import com.virtualworld.contadorkm.domain.utils.RunUtils
import com.virtualworld.contadorkm.navegation.DestinationApp
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    bottomPadding: Dp = 0.dp,
    navController: NavController
) {
    val doesUserExist by viewModel.doesUserExist.collectAsStateWithLifecycle()
    val state by viewModel.homeScreenState.collectAsStateWithLifecycle()
    val durationInMillis by viewModel.durationInMillis.collectAsStateWithLifecycle()

    if (doesUserExist == true)
        HomeScreenContent(
            bottomPadding = bottomPadding,
            state = state,
            durationInMillis = durationInMillis,
            deleteRun = viewModel::deleteRun,
            showRun = viewModel::showRun,
            dismissDialog = viewModel::dismissRunDialog,
            navigateToRunScreen = { DestinationApp.navigateToCurrentRunScreen(navController) },
            navigateToRunningHistoryScreen = {
                DestinationApp.Home.RecentRun.navigateToRunningHistoryScreen(navController)
            }
        )

    LaunchedEffect(key1 = doesUserExist) {
        if (doesUserExist == false)
            DestinationApp.Home
                .navigateToOnBoardingScreen(navController)
    }
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
    navigateToRunningHistoryScreen: () -> Unit
) {
    Column {
        TopBar(
            modifier = Modifier
                .zIndex(1f),
            user = state.user,
            weeklyGoalInKm = state.user.weeklyGoalInKM,
            distanceCoveredInCurrentWeekInKm = state.distanceCoveredInKmInThisWeek
        )
        if (durationInMillis > 0)
            CurrentRunningCard(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(top = 28.dp)
                    .clickable(onClick = navigateToRunScreen),
                durationInMillis = durationInMillis,
                runState = state.currentRunStateWithCalories,
            )
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(vertical = 28.dp, horizontal = 24.dp)
        ) {
            Text(
                text = "Recent Activity",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier
                    .weight(1f)
            )
            Text(
                text = "All",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .clickable(onClick = navigateToRunningHistoryScreen, role = Role.Button)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(bottom = bottomPadding)
        ) {
            if (state.runList.isEmpty())
                EmptyRunListView(
                    modifier = Modifier
                )
            else
                RecentRunList(
                    runList = state.runList,
                    onItemClick = showRun
                )
        }
    }
    state.currentRunInfo?.let {
        RunInfoDialog(
            run = it,
            onDismiss = dismissDialog,
            onDelete = deleteRun
        )
    }
}

@Composable
private fun RecentRunList(
    modifier: Modifier = Modifier,
    runList: List<Run>,
    onItemClick: (Run) -> Unit,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .padding(horizontal = 24.dp)
            .padding(bottom = 8.dp)
            .wrapContentHeight()
    ) {
        Column {
            runList.forEachIndexed { i, run ->
                Column(
                    modifier = Modifier
                ) {
                    RunItem(
                        run = run,
                        modifier = Modifier
                            .clickable { onItemClick(run) }
                            .padding(16.dp)
                    )
                    if (i < runList.lastIndex)
                        Box(
                            modifier = Modifier
                                .height(1.dp)
                                .width(200.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                        alpha = 0.2f
                                    )
                                )
                                .align(Alignment.CenterHorizontally)
                        )
                }
            }
        }
    }

}

@Composable
@Preview(showBackground = true)
private fun EmptyRunListView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_calendar),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.size(16.dp))
        val inlineContentMap = mapOf(
            "run_icon_img" to InlineTextContent(
                placeholder = Placeholder(
                    MaterialTheme.typography.bodyLarge.fontSize,
                    MaterialTheme.typography.bodyLarge.fontSize,
                    PlaceholderVerticalAlign.TextCenter
                )
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_run),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.5f)
                )
            }
        )
        Text(
            text = buildAnnotatedString {
                append("Its seems like we don't have any records. Record you run by clicking on the ")
                appendInlineContent(id = "run_icon_img")
                append(" button")
            },
            inlineContent = inlineContentMap,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )
    }
}

@Composable
private fun CurrentRunningCard(
    modifier: Modifier = Modifier,
    runState: CurrentRunStateWithCalories,
    durationInMillis: Long = 0
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(
                    topStartPercent = 50,
                    topEndPercent = 50,
                    bottomEndPercent = 50,
                    bottomStartPercent = 50
                )
            )
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape
                ),
        ) {
            Image(
                painter = painterResource(id = R.drawable.running_boy),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .offset(x = 8.dp)
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "Current Session",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )
            Spacer(modifier = Modifier.size(2.dp))
            Text(
                text = RunUtils.getFormattedStopwatchTime(durationInMillis),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
        Column(
            modifier = Modifier
        ) {
            Text(
                text = "${runState.currentRunState.distanceInMeters / 1000f} km",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )
            Spacer(modifier = Modifier.size(2.dp))
            Text(
                text = "${runState.caloriesBurnt} kcal",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}

