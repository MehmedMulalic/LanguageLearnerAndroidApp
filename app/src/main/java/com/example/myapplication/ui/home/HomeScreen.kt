package com.example.myapplication.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val username: String by homeViewModel.username.collectAsState()

    HomeScreenForm(username)
}

@Composable
fun HomeScreenForm(username: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                "Welcome Back $username",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(36.dp))
            Button(
                onClick = {},
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    "Start",
                    fontSize = 32.sp
                )
                Icon(
                    Icons.Default.PlayArrow,
                    null,
                    modifier = Modifier.size(42.dp)
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Card(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vestibulum elit at auctor varius. Pellentesque efficitur auctor orci, nec facilisis nibh vestibulum eget. Sed sollicitudin, turpis sit amet cursus luctus, eros erat ornare est, at maximus lorem justo dapibus orci. Nam blandit diam eros, mattis accumsan sapien dignissim vel. Duis gravida, ante non aliquam pretium, neque metus dictum lorem, ac sodales enim leo vel metus. Integer quis orci commodo, dictum sem nec, viverra tortor. Nulla facilisi. Phasellus cursus vel magna et mollis. Curabitur dictum fermentum lacus id dignissim. Vivamus in nunc lacus. In aliquet laoreet odio, at efficitur velit imperdiet eget. Duis in elit arcu. Nunc viverra mollis nunc a euismod. Sed pulvinar vulputate velit, eu tristique lacus egestas ac. Suspendisse mi dui, bibendum non accumsan non, finibus in nibh. Ut ipsum velit, dictum vel justo at, accumsan maximus purus.\n",
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreenForm("Mele")
}