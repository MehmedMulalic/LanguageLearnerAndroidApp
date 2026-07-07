package com.mmulalic.languagelearner.ui.main.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mmulalic.languagelearner.ui.main.home.ProfileItem

@Composable
fun ProfileScreen(
    onSignoutSucces: () -> Unit,
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val state by profileViewModel.state.collectAsState()
    val options = listOf(
        ProfileItem("Settings") {}, //TODO
        ProfileItem("Logout") {
            profileViewModel.logout()
        }
    )

    LaunchedEffect(state) {
        if (state is ProfileState.LoggedOut) {
            onSignoutSucces()
        }
    }

    when (state) {
        ProfileState.Loading -> LoadingForm()
        ProfileState.LoggedIn -> ProfileForm(modifier, options)
        ProfileState.LoggedOut -> LoadingForm()
        is ProfileState.Error -> ProfileForm(modifier, options)
    }
}

@Composable
fun LoadingForm() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(64.dp))
    }
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
