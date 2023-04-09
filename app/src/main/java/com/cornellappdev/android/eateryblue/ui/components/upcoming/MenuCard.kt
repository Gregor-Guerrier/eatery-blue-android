package com.cornellappdev.android.eateryblue.ui.components.upcoming

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import com.cornellappdev.android.eateryblue.R
import com.cornellappdev.android.eateryblue.data.models.Eatery
import com.cornellappdev.android.eateryblue.ui.components.general.Filter
import com.cornellappdev.android.eateryblue.ui.screens.EateryMenuWidget
import com.cornellappdev.android.eateryblue.ui.theme.EateryBlueTypography
import com.cornellappdev.android.eateryblue.ui.theme.GrayFive
import com.cornellappdev.android.eateryblue.ui.theme.GrayZero
import com.cornellappdev.android.eateryblue.ui.theme.Green
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterialApi::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun MenuCard(
    eatery: Eatery,
    modifier: Modifier = Modifier.fillMaxWidth(),
    day: Int,
    meal: SnapshotStateList<Filter>,
    selectEatery: (eatery: Eatery) -> Unit = {},

    ) {
    var openDropdown by remember { mutableStateOf(false) }

    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.White,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(start = 12.dp, top = 12.dp, bottom = 12.dp)) {
            Box(modifier = Modifier.fillMaxWidth()) {

                if (eatery.isClosed()) {
                    var text = eatery.name ?: ""
                    if (text.length > 20) {
                        text = text.substring(0, 20)
                        text = text.trim()
                        text = "$text..."
                    }

                    Text(
                        text = text,
                        style = EateryBlueTypography.h5,
                    )
                    Text(
                        modifier = Modifier.padding(top = 20.dp),
                        text = "Closed",
                        color = Red
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 12.dp)
                    ) {
                        ClosedEateryDetails(
                            eatery = eatery,
                            selectEatery = { selectEatery(eatery) },
                        )
                    }

                } else {
                    Text(
                        text = eatery.name ?: "",
                        style = EateryBlueTypography.h5,
                    )


                    IconButton(
                        onClick = {
                            openDropdown = !openDropdown
                        },
                        modifier = Modifier
                            .padding(top = 10.dp, end = 12.dp)
                            .size(28.dp)
                            .background(color = GrayZero, shape = CircleShape)
                            .align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = if (!openDropdown) Icons.Default.ExpandMore else Icons.Default.ExpandLess,
                            contentDescription = "Expand menu",
                            tint = Color.Black
                        )
                    }
                    Row {
                        Text(
                            modifier = Modifier.padding(top = 20.dp),
                            text = "Open",
                            color = Green
                        )
                        val event = eatery.getTodaysEvents()[0]
                        Text(
                            text = "${event.startTime!!.format(DateTimeFormatter.ofPattern("K:mm a"))} - ${
                                event.endTime!!.format(
                                    DateTimeFormatter.ofPattern("K:mm a")
                                )
                            }",
                            style = EateryBlueTypography.subtitle2,
                            color = GrayFive,
                            modifier = Modifier.padding(start = 10.dp, top = 20.dp)
                        )
                    }
                }
            }

            if (openDropdown) {
                Spacer(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 16.dp, top = 12.dp, bottom = 8.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(GrayZero, CircleShape)
                )
                Box(modifier = Modifier.padding(end = 12.dp)) {
                    OpenEateryDetails(
                        eatery = eatery,
                        selectEatery = { selectEatery(eatery) },
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClosedEateryDetails(
    eatery: Eatery,
    selectEatery: (eatery: Eatery) -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        onClick = {
            selectEatery(eatery)
        },
        backgroundColor = GrayZero,
    ) {
        Row(
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 10.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_eatery),
                contentDescription = null,
                tint = Color.Black
            )
            Text(
                text = "Eatery Details",
                color = Color.Black,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OpenEateryDetails(
    eatery: Eatery,
    selectEatery: (eatery: Eatery) -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        onClick = {
            selectEatery(eatery)
        },
        backgroundColor = GrayZero,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 10.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_eatery),
                contentDescription = null,
                tint = Color.Black
            )
            eatery.getTodaysEvents().forEach { event ->
                EateryMenuWidget(event = event)
            }
            Text(
                text = "View Eatery Details",
                color = Color.Black,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

    }

}
