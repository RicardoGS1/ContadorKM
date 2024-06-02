package com.virtualworld.contadorkm.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.virtualworld.contadorkm.R
import kotlin.math.roundToInt

@Composable
@Preview(showBackground = true)
fun TopBar(
    modifier: Modifier = Modifier,
    user: User = User(),
    weeklyGoalInKm: Float = 0f,
    distanceCoveredInCurrentWeekInKm: Float = 0f
) {
    Box(
        modifier = modifier
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .offset(y = (-24).dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
                )
        )
        Column(modifier = modifier.padding(horizontal = 24.dp)) {
            Spacer(modifier = Modifier.size(24.dp))
            TopBarProfile(
                modifier = Modifier.background(color = Color.Transparent),
                //user = user
            )
            Spacer(modifier = Modifier.size(32.dp))
            WeeklyGoalCard(
                weeklyGoalInKm = weeklyGoalInKm.roundToInt(),
                weeklyGoalDoneInKm = distanceCoveredInCurrentWeekInKm
            )
        }
    }

}

@Composable
private fun TopBarProfile(
    modifier: Modifier = Modifier,
    //user: User
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserProfilePic(
            imgUri = user.imgUri,
            gender = user.gender,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.size(12.dp))

        Text(
            text = buildAnnotatedString {
                append("Hello, ")
                withStyle(
                    style = SpanStyle(fontWeight = FontWeight.SemiBold),
                ) {
                    append(user.name)
                }
            },
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier
                .weight(1f)
        )

        IconButton(
            onClick = {},
            modifier = Modifier
                .size(24.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_settings),
                contentDescription = "Settings",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

    }
}

@Composable
private fun WeeklyGoalCard(
    modifier: Modifier = Modifier,
    weeklyGoalInKm: Int,
    weeklyGoalDoneInKm: Float
) {
    ElevatedCard(
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "Weekly Goal",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                ),
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "$weeklyGoalInKm km",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier
                    .weight(1f)
            )
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_forward),
                contentDescription = "More info",
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.CenterVertically),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "$weeklyGoalDoneInKm km done",
                    modifier = Modifier
                        .weight(1f),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = "${
                        (weeklyGoalInKm - weeklyGoalDoneInKm).coerceIn(
                            0f,
                            weeklyGoalInKm.toFloat()
                        )
                    } km left",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
            Spacer(
                modifier = Modifier.size(8.dp)
            )
            LinearProgressIndicator(
                progress = if (weeklyGoalInKm > 0) weeklyGoalDoneInKm / weeklyGoalInKm else 0f,
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                strokeCap = StrokeCap.Round,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
        }
    }

}
