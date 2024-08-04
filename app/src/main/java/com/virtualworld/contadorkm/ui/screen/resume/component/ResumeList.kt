package com.virtualworld.contadorkm.ui.screen.resume.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.virtualworld.contadorkm.domain.utils.RunUtils.getFormattedStopwatchTime
import com.virtualworld.contadorkm.ui.screen.resume.ResumeScreenStateDistances
import com.virtualworld.contadorkm.ui.screen.resume.ResumeScreenStateSpeed
import com.virtualworld.contadorkm.ui.screen.resume.ResumeScreenStateTime


@Composable
fun ResumeList(stateDistance: ResumeScreenStateDistances, stateTime: ResumeScreenStateTime, stateSpeed: ResumeScreenStateSpeed)
{

    //RunUtils.getFormattedStopwatchTime

    Column() {

        ItemResumeDistance(stateDistance)

        ItemResumeTime(stateTime)

        ItemResumeSpeed(stateSpeed)

    }

}

@Composable
fun ItemResumeSpeed(stateSpeed: ResumeScreenStateSpeed)
{
    when (stateSpeed)
    {
        is ResumeScreenStateSpeed.Error -> ErrorItem()
        is ResumeScreenStateSpeed.Loading -> LoadingItem()
        is ResumeScreenStateSpeed.Success -> SuccessItem(stateSpeed.name, stateSpeed.speedMax.toString(), stateSpeed.speedAvg.toString(), null)
    }
}

@Composable
fun ItemResumeDistance(stateDistance: ResumeScreenStateDistances)
{

        SuccessItem(stateDistance.name,
                                                            stateDistance.distanceMax.toString(),
                                                            stateDistance.distanceAvg.toString(),
                                                            stateDistance.distanceTotal.toString())

}

@Composable
private fun ItemResumeTime(stateTime: ResumeScreenStateTime)
{
    when (stateTime)
    {
        is ResumeScreenStateTime.Error -> ErrorItem()
        is ResumeScreenStateTime.Loading -> LoadingItem()
        is ResumeScreenStateTime.Success -> SuccessItem(stateTime.name,
                                                        getFormattedStopwatchTime(stateTime.timeMax),
                                                        getFormattedStopwatchTime(stateTime.timeAvg),
                                                        getFormattedStopwatchTime(stateTime.timeTotal))
    }

}

@Composable
fun SuccessItem(name: String, maximo: String, avg: String, total: String?)
{
    ElevatedCard(elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {

        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
            Text(text = name, style = MaterialTheme.typography.titleLarge

            )
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 8.dp)
                    .height(IntrinsicSize.Min),
            ) {
                Column {
                    Text(text = "Maximo")
                    Text(text = maximo.toString())
                }

                Box(modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .padding(vertical = 8.dp)
                    .align(Alignment.CenterVertically)
                    .background(
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    ))

                Column {
                    Text(text = "Promedio")
                    Text(text = avg.toString())
                }
                if (total != null)
                {
                    Box(modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .padding(vertical = 8.dp)
                        .align(Alignment.CenterVertically)
                        .background(
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                        ))
                    Column {
                        Text(text = "Total")
                        Text(text = total.toString())
                    }

                }
            }
        }
    }
}

@Composable
fun LoadingItem()
{

}

@Composable
fun ErrorItem()
{

}
