package com.virtualworld.contadorkm.ui.screen.resume

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.virtualworld.contadorkm.ui.screen.resume.component.ResumeList
import com.virtualworld.contadorkm.ui.screen.resume.resumeState.ResumeScreenStateDistances
import com.virtualworld.contadorkm.ui.screen.resume.resumeState.ResumeScreenStateSpeed
import com.virtualworld.contadorkm.ui.screen.resume.resumeState.ResumeScreenStateTimes
import com.virtualworld.contadorkm.ui.screen.resume.resumeState.TimeRange

@Composable
fun ResumeScreen(viewModel: ResumeViewModel = hiltViewModel(), bottomPadding: Dp = 0.dp, navController: NavController){

    val stateDistance by viewModel.resumeScreenStateDistance.collectAsStateWithLifecycle()
    val stateTime by viewModel.resumeScreenStateTime.collectAsStateWithLifecycle()
    val stateSpeed by viewModel.resumeScreenStateSpeed.collectAsStateWithLifecycle()
    val fromDate by viewModel.fromDate.collectAsStateWithLifecycle()


    LaunchedEffect(fromDate) {
        viewModel.changerFecha(fromDate)
    }



    ResumeScreenContent(bottomPadding = bottomPadding,
                        stateDistance,stateTime,stateSpeed,viewModel)





}

@Composable
fun ResumeScreenContent(bottomPadding: Dp,
                        stateDistance: ResumeScreenStateDistances,
                        stateTime: ResumeScreenStateTimes,
                        stateSpeed: ResumeScreenStateSpeed,
                        viewModel: ResumeViewModel)
{



    var selectedOption by remember {
        mutableStateOf<TimeRange>(TimeRange.All)
    }
    val onSelectionChange = { timeRange: TimeRange ->
        selectedOption = timeRange
       viewModel.onSelectionChange(timeRange)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = bottomPadding)
    ){
        Text(text = "Resumen de Carreras", fontSize = 32.sp, modifier = Modifier
            .padding(16.dp)
            .align(Alignment.CenterHorizontally))


        TimeRangeSelector(selectedOption, onSelectionChange)

        ResumeList(stateDistance, stateTime, stateSpeed)

    }


}

@Composable
fun TimeRangeSelector(
    selectedOption: TimeRange,
    onSelectionChange: (TimeRange) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        items(TimeRange.valores()) { timeRange ->
            TimeRangeButton(timeRange, timeRange == selectedOption, onSelectionChange)
        }
    }
}

@Composable
fun TimeRangeButton(
    timeRange: TimeRange,
    selected: Boolean,
    onSelectionChange: (TimeRange) -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) { MaterialTheme.colorScheme.primary} else {Color( Color.LightGray.value)}),
        onClick = { onSelectionChange(timeRange) }
    ) {
        Text(text = timeRange.description)
    }
}


