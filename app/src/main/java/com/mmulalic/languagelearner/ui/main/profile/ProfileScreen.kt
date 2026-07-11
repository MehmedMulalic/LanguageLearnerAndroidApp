package com.mmulalic.languagelearner.ui.main.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun ProfileScreen(
    onSignOutSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val state by profileViewModel.state.collectAsState()

    LaunchedEffect(state) {
        if (state is ProfileState.LoggedOut) {
            onSignOutSuccess()
        }
    }

    when (state) {
        ProfileState.Loading, ProfileState.LoggedOut -> LoadingForm()
        ProfileState.LoggedIn, is ProfileState.Error -> ProfileForm(
            modifier = modifier,
            actions = ProfileActions(
                onProfileClick = {},
                onEmailClick = {},
                onChangePasswordClick = {},
                onNotificationsClick = {},
                onDarkModeClick = {},
                onLogoutClick = profileViewModel::logout
            )
        )
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
    actions: ProfileActions = ProfileActions()
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column {
                SettingsHeader("Account")
                SettingsCard {
                    SettingsRow(
                        "Profile",
                        actions.onProfileClick,
                        Icons.Outlined.Person
                    )
                    SettingsRow(
                        "Email Address",
                        actions.onEmailClick,
                        Icons.Outlined.Mail
                    )
                    SettingsRow(
                        "Change Password",
                        actions.onChangePasswordClick,
                        Icons.Outlined.Key
                    )
                }
            }

            Column {
                SettingsHeader("Personalization")
                SettingsCard {
                    SettingsRow(
                        "Notifications",
                        actions.onNotificationsClick,
                        Icons.Outlined.Notifications
                    )
                    SettingsRow(
                        "Dark Mode",
                        actions.onDarkModeClick,
                        Icons.Outlined.DarkMode
                    )
                }
            }

            Column {
                SettingsCard {
                    SettingsRow(
                        "Log Out",
                        actions.onLogoutClick,
                        Icons.AutoMirrored.Outlined.Logout,
                        MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsHeader(
    label: String
) {
    Text(
        label,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.padding(horizontal = 24.dp)
    )
}

@Composable
fun SettingsCard(
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(content = content)
    }
}

@Composable
fun SettingsRow(
    label: String,
    onClick: () -> Unit,
    icon: ImageVector,
    color: Color? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = color ?: LocalContentColor.current
        )
        Text(
            text = label,
            color = color ?: LocalContentColor.current,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = color ?: LocalContentColor.current
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileFormPreview() {
    ProfileForm(Modifier)
}
