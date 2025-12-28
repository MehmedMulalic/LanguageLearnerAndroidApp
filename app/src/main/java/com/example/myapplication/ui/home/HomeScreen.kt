package com.example.myapplication.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val username: String by homeViewModel.username.collectAsState()
    val streak: Int = 5 //TODO()

    HomeScreenForm(username, streak)
}

@Composable
fun HomeScreenForm(username: String, streak: Int) {
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
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                buildAnnotatedString {
                    append("Your current streak is ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("$streak")
                    }
                    append(" days")
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(36.dp))
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
                    modifier = Modifier.size(42.dp)
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Card(
                modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth()
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                        )) {
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
                        withStyle(style = SpanStyle(
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                        )) {
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
                        withStyle(style = SpanStyle(
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                        )) {
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
    HomeScreenForm("Mele", 5)
}