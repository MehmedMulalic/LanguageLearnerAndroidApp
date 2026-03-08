package com.mmulalic.languagelearner.ui.main.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmulalic.languagelearner.ui.main.MainViewModel

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val state by mainViewModel.uiState.collectAsState()

    when (val currentState = state) {
        is HomeUiState.Loading -> {
            CircularProgressIndicator(Modifier.fillMaxSize())
        }
        is HomeUiState.Success -> {
            HomeScreenForm(
                modifier,
                currentState.username
            )
        }
        is HomeUiState.Error -> {
            Text(currentState.message)
        }
    }
}

@Composable
fun HomeScreenForm(
    modifier: Modifier = Modifier,
    username: String
) {
    val cardSpanState = SpanStyle(
        fontSize = MaterialTheme.typography.titleLarge.fontSize,
        fontWeight = FontWeight.Medium
    )

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .offset(y = (-60).dp)
                .padding(24.dp)
        ) {
            Text(
                "Welcome Back $username",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Streak UI, removed for now
//            Text(
//                buildAnnotatedString {
//                    append("Your current streak is ")
//                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
//                        append("$streak")
//                    }
//                    append(" days")
//                },
//                modifier = Modifier.align(Alignment.CenterHorizontally),
//                fontSize = 18.sp
//            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {},
                modifier = Modifier.align(Alignment.CenterHorizontally).width(200.dp)
            ) {
                Text(
                    "Start",
                    fontSize = 32.sp
                )
                Icon(
                    Icons.Default.PlayArrow,
                    null,
                    modifier = Modifier.size(38.dp)
                )
            }
            Spacer(modifier = Modifier.height(64.dp))
            Card(
                modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth()
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(style = cardSpanState) {
                            append("Today's Overview\n\n")
                        }
                        append("Ipsum Lorem")
                    },
                    modifier = Modifier.padding(18.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth()
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(style = cardSpanState) {
                            append("Statistics\n\n")
                        }
                        append("Ipsum Lorem")
                    },
                    modifier = Modifier.padding(18.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth()
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(style = cardSpanState) {
                            append("Latest words\n\n")
                        }
                        append("Ipsum Lorem")
                    },
                    modifier = Modifier.padding(18.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreenForm(username = "Mele")
}

@Preview(showBackground = true)
@Composable
fun PreviewNavigationDrawer() {
    val drawerState = rememberDrawerState(DrawerValue.Open)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp)
            ) {
                Text(
                    "Something User Related?",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text(text = "Logout", style = MaterialTheme.typography.bodyMedium) },
                    selected = false,
                    onClick = {
                        // TODO() user logs out
                    }
                )
            }
        }
    ) {
        // TODO() ovdje ide HomeScreenForm()
    }
}