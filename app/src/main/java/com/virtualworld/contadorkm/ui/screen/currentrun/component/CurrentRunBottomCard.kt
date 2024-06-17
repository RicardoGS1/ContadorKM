package com.virtualworld.contadorkm.ui.screen.currentrun.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.virtualworld.contadorkm.R
import com.virtualworld.contadorkm.domain.utils.RunUtils

import com.virtualworld.contadorkm.domain.model.CurrentRunStateUI
import com.virtualworld.contadorkm.navegation.slideDownInDuration
import com.virtualworld.contadorkm.navegation.slideDownOutDuration


//animacion de RunningCard
@Composable
fun CurrentRunBottomCard(visible: Boolean,
                         modifier: Modifier,
                         durationInMillis: Long = 0L,
                         runState: CurrentRunStateUI,
                         onPlayPauseButtonClick: () -> Unit = {},
                         onFinish: () -> Unit)
{

    AnimatedVisibility(visible = visible,
                       modifier = modifier,
                       enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight },
                                                 animationSpec = tween(durationMillis = slideDownInDuration, easing = LinearOutSlowInEasing)),
                       exit = slideOutVertically(targetOffsetY = { fullHeight -> fullHeight },
                                                 animationSpec = tween(durationMillis = slideDownOutDuration, easing = FastOutLinearInEasing)),
                       content = {
                           RunningCard(Modifier.padding(vertical = 16.dp, horizontal = 24.dp), durationInMillis, runState, onPlayPauseButtonClick, onFinish)
                       })
}


@Composable
fun RunningCard(modifier: Modifier = Modifier,
                durationInMillis: Long = 0L,
                runState: CurrentRunStateUI,
                onPlayPauseButtonClick: () -> Unit = {},
                onFinish: () -> Unit)
{
    ElevatedCard(modifier = modifier.fillMaxWidth(), elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)) {

        RunningCardTime(modifier = Modifier
            .padding(top = 8.dp, start = 24.dp, end = 16.dp, bottom = 8.dp)
            .fillMaxWidth(),
                        durationInMillis = durationInMillis,
                        isRunning = runState.currentRunState.isTracking,
                        onPlayPauseButtonClick = onPlayPauseButtonClick,
                        onFinish = onFinish)




        Row(horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 4.dp)
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)

        ) {

            RunningStatsItem(modifier = Modifier, unit = "km", value = (runState.currentRunState.distanceInMeters / 1000f).toString())
            Box(modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .padding(vertical = 8.dp)
                .align(Alignment.CenterVertically)
                .background(
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                ))

            RunningStatsItem(modifier = Modifier, unit = "km/hr", value = runState.currentRunState.speedInKMH.toString())
        }

    }
}




@Composable
private fun RunningCardTime(modifier: Modifier = Modifier, durationInMillis: Long, isRunning: Boolean, onPlayPauseButtonClick: () -> Unit, onFinish: () -> Unit)
{
    Row(modifier = modifier.fillMaxWidth()) {

        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Running Time",
                 style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Normal),
                 color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(
                text = RunUtils.getFormattedStopwatchTime(durationInMillis),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
            )
        }

        if (!isRunning && durationInMillis > 0)
        {
            IconButton(onClick = onFinish,
                       modifier = Modifier

                           .size(40.dp)
                           .background(color = MaterialTheme.colorScheme.error, shape = MaterialTheme.shapes.medium)
                           .align(Alignment.CenterVertically)) {
                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_finish),
                     contentDescription = "",
                     modifier = Modifier.size(24.dp),
                     tint = MaterialTheme.colorScheme.onError)
            }
            Spacer(modifier = Modifier.size(16.dp))
        }
        IconButton(onClick = onPlayPauseButtonClick,
                   modifier = Modifier

                       .size(40.dp)
                       .background(color = MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.medium)
                       .align(Alignment.CenterVertically)) {
            Icon(imageVector = ImageVector.vectorResource(id = if (isRunning) R.drawable.ic_pause else R.drawable.ic_play),
                 contentDescription = "",
                 modifier = Modifier.size(24.dp),
                 tint = MaterialTheme.colorScheme.onPrimary)
        }
    }
}




@Composable
fun RunningStatsItem(
    modifier: Modifier = Modifier,

    unit: String,
    value: String
) {
    Row(modifier = modifier.padding(4.dp)) {


        Column(
            modifier = Modifier
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = value,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            )
            Text(
                text = unit,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}