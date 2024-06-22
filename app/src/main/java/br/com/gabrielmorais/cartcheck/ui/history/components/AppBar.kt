package br.com.gabrielmorais.cartcheck.ui.history.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
  title: String,
  onClick: () -> Unit = {}
) {
  TopAppBar(
    title = {
      Text(
        text = title,
        style = TextStyle(
          fontSize = 30.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.primary
        ),
      )
    },
    navigationIcon = {
      IconButton(onClick = onClick) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
      }
    }
  )
}