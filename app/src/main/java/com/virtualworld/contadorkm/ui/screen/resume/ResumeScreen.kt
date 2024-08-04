package com.virtualworld.contadorkm.ui.screen.resume

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

    val options = listOf(
        "Todo",
        "Ano",
        "Mes",
        "Semana",

    )

    var selectedOption by remember {
        mutableStateOf("Todo")
    }
    val onSelectionChange = { text: String ->
        selectedOption = text
       viewModel.onSelectionChange(text)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = bottomPadding)
    ){
        Text(text = "Resumen de Carreras", fontSize = 32.sp, modifier = Modifier
            .padding(16.dp)
            .align(Alignment.CenterHorizontally))

       // MaterialButtonToggleGroup()

        //CustomRadioGroup()

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,){

            options.forEach {
                Button(

                    colors = ButtonDefaults.buttonColors(

                        containerColor = if (it == selectedOption) { MaterialTheme.colorScheme.primary} else {Color( Color.LightGray.value)},
                       // contentColor = Color(0xFFFFEE58)
                    ),

                    onClick = {onSelectionChange(it) }
                ) {
                    Text(text = it)
                }
            }



        }



        ResumeList(stateDistance,stateTime,stateSpeed)
    }




}


