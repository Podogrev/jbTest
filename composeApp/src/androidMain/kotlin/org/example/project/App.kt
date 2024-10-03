package org.example.project

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            val items = List(100) { "Item $it" }
            LazyColumnExample(items)
        }
    }
}


@Composable
fun LazyColumnExample(items: List<String>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(30.dp)
    ) {
        items(items) { item ->
            Text(
                text = item,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}