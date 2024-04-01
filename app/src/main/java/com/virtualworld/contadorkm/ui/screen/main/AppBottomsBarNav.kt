package com.virtualworld.contadorkm.ui.screen.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.virtualworld.contadorkm.R
import com.virtualworld.contadorkm.navegation.BottomNavDestination
import com.virtualworld.contadorkm.navegation.Destination
import com.virtualworld.contadorkm.navegation.navigateToBottomNavDestination


const val slideDownInDuration = 250
const val slideDownOutDuration = 250

@Composable
fun AppBottomFAB(navHostController: NavController,visible: Boolean)
{
    AnimatedVisibility(visible = visible,
                       enter = scaleIn(animationSpec = tween(150)),
                       exit = scaleOut(animationSpec = tween(150)),
                      ){  FloatingActionButton(onClick = { navHostController.navigate(Destination.CurrentRun.route) },
                                               contentColor = MaterialTheme.colorScheme.onPrimary,
                                               containerColor = MaterialTheme.colorScheme.primary,
                                               shape = CircleShape,
                                               modifier = Modifier
                                                   .size(80.dp)
                                                   .offset(y = 50.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_run),
            contentDescription = "",
        )
    }
    }
}



@Composable
fun AppBottomBar(visible: Boolean, navController: NavController)
{

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight },
                                                 animationSpec = tween(durationMillis = slideDownInDuration, easing = LinearOutSlowInEasing)),
        exit = slideOutVertically(targetOffsetY = { fullHeight -> fullHeight },
                                                 animationSpec = tween(durationMillis = slideDownOutDuration, easing = FastOutLinearInEasing)),
        content = {


                           Box(modifier = Modifier
                               .padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
                               .shadow(elevation = 8.dp,
                                       shape = RoundedCornerShape(16.dp),
                                       ambientColor = MaterialTheme.colorScheme.primary,
                                       spotColor = MaterialTheme.colorScheme.primary,
                                       clip = true)) {
                               BottomNavBar(navController = navController,
                                            items = listOf(BottomNavDestination.Home, BottomNavDestination.Profile),
                                            )
                           }
                       })
}

@Composable
private fun BottomNavBar(navController: NavController,  items: List<BottomNavDestination>)
{

    NavigationBar(

        containerColor = MaterialTheme.colorScheme.surface,

        ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { item ->

            NavigationBarItem(
                icon = {
                    Icon(imageVector = item.getIconVector(), contentDescription = "")
                },
                selected = currentDestination?.hierarchy?.any {
                    it.route == item.route
                } == true,
                onClick = { navController.navigateToBottomNavDestination(item) },

                )
        }
    }
}

