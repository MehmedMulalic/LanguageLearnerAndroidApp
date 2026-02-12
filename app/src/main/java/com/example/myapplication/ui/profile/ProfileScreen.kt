package com.example.myapplication.ui.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myapplication.ui.Home
import com.example.myapplication.ui.Login
import com.example.myapplication.ui.home.ProfileItem

@Composable
fun ProfileScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val options = listOf(
        ProfileItem("Settings") {

        },
        ProfileItem("Logout") {
            profileViewModel.logout()
            navController.navigate(Login) {
                popUpTo(Home) { inclusive = true }
            }
        }
    )

    ProfileForm(modifier, options)
}

@Composable
fun ProfileForm(
    modifier: Modifier = Modifier,
    options: List<ProfileItem>
) {
    Column {
        options.forEach { (label, onClick) ->
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable { onClick() }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    label,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            HorizontalDivider()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileFormPreview() {
    ProfileForm(Modifier,listOf(ProfileItem("Test") {}))
}
