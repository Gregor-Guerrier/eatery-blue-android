package com.cornellappdev.android.eateryblue.ui.components.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cornellappdev.android.eateryblue.ui.theme.EateryBlue
import com.cornellappdev.android.eateryblue.ui.theme.EateryBlueTypography
import com.cornellappdev.android.eateryblue.ui.theme.GrayOne
import com.cornellappdev.android.eateryblue.ui.theme.GrayZero
import com.cornellappdev.android.eateryblue.util.AppIcon
import com.cornellappdev.android.eateryblue.util.changeIcon
import com.cornellappdev.android.eateryblue.util.currentIcon
import com.cornellappdev.android.eateryblue.util.iconMap

@Composable
fun AppIconBottomSheet(hide: () -> Unit) {
    val context = LocalContext.current
    val (selectedAppIcon, setSelectedAppIcon) = remember { mutableStateOf(currentIcon(context)) }
    val currentIcon = currentIcon(context)
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "App Icon",
                style = EateryBlueTypography.h4,
                color = Color.Black,
            )

            IconButton(
                onClick = {
                    hide()
                },
                modifier = Modifier
                    .size(40.dp)
                    .background(color = GrayZero, shape = CircleShape)
            ) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Black)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            AppIconButton(AppIcon.DEFAULT, selectedAppIcon, setSelectedAppIcon)
            AppIconButton(AppIcon.BLUE, selectedAppIcon, setSelectedAppIcon)
            AppIconButton(AppIcon.RED, selectedAppIcon, setSelectedAppIcon)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            AppIconButton(AppIcon.GREEN, selectedAppIcon, setSelectedAppIcon)
            AppIconButton(AppIcon.YELLOW, selectedAppIcon, setSelectedAppIcon)
            AppIconButton(AppIcon.ORANGE, selectedAppIcon, setSelectedAppIcon)
        }

        Button(
            onClick =
            {
                changeIcon(context, selectedAppIcon)
                hide()
            },
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = EateryBlue,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Done",
                style = EateryBlueTypography.h5,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
        }

        TextButton(
            enabled = selectedAppIcon != currentIcon,
            onClick = {
                setSelectedAppIcon(currentIcon)
                hide()
            },
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Reset",
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold),
                color = if (selectedAppIcon != currentIcon) Color.Black else GrayOne
            )
        }
    }
}

@Composable
fun AppIconButton(
    appIcon: AppIcon,
    selectedAppIcon: AppIcon,
    setSelectedAppIcon: (AppIcon) -> Unit
) {
    val interactionSource = MutableInteractionSource()

    Surface(
        modifier = Modifier
            .padding(start = 18.dp, bottom = 12.dp)
            .border(1.dp, GrayOne, shape = RoundedCornerShape(25.dp))
            .shadow(1.dp, RoundedCornerShape(25.dp), clip = true)
    ) {
        Box {
            Image(
                painter = painterResource(id = iconMap[appIcon]!!.second),
                contentDescription = null,
                alpha = if (appIcon == selectedAppIcon) ContentAlpha.disabled else 1f,
                modifier = Modifier
                    .size(72.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = rememberRipple()
                    ) {
                        setSelectedAppIcon(appIcon)
                    }
            )
            if (appIcon == selectedAppIcon) {
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.Center),
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = EateryBlue
                )
            }
        }
    }
}
