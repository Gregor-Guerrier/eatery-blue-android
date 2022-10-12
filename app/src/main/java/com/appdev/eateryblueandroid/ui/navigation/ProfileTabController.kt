package com.appdev.eateryblueandroid.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.appdev.eateryblueandroid.ui.screens.settings.*
import com.appdev.eateryblueandroid.ui.viewmodels.BottomSheetViewModel
import com.appdev.eateryblueandroid.ui.viewmodels.EateryDetailViewModel
import com.appdev.eateryblueandroid.ui.viewmodels.HomeViewModel
import com.appdev.eateryblueandroid.ui.viewmodels.ProfileViewModel

@Composable
fun ProfileTabController(
    profileViewModel: ProfileViewModel,
    bottomSheetViewModel: BottomSheetViewModel,
    eateryState: State<HomeViewModel.State>,
    profileEateryDetailViewModel: EateryDetailViewModel
) {
    val display = profileViewModel.display.collectAsState()

    display.value.let {
        when (it) {
            is ProfileViewModel.Display.Login ->
                LoginScreen(
                    profileViewModel = profileViewModel
                )
            is ProfileViewModel.Display.Profile ->
                ProfileScreen(
                    profileViewModel = profileViewModel,
                    bottomSheetViewModel = bottomSheetViewModel
                )
            is ProfileViewModel.Display.Settings ->
                SettingsScreen(profileViewModel)
            is ProfileViewModel.Display.About ->
                AboutScreen(profileViewModel)
            is ProfileViewModel.Display.Favorites ->
                FavoritesScreen(profileViewModel, eateryState, profileEateryDetailViewModel)
            is ProfileViewModel.Display.Notifications ->
                NotificationsScreen(profileViewModel)
            is ProfileViewModel.Display.Privacy ->
                PrivacyScreen(profileViewModel)
            is ProfileViewModel.Display.Legal ->
                LegalScreen(profileViewModel)
            is ProfileViewModel.Display.Support ->
                SupportScreen(profileViewModel)
            is ProfileViewModel.Display.EateryDetailVisible ->
                EateryDetailScreen(
                    eateryDetailViewModel = profileEateryDetailViewModel,
                    hideEatery = profileViewModel::transitionFavorites
                )

        }
    }
}