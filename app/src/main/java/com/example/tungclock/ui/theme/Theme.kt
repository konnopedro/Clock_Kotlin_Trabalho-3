package com.example.tungclock.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

private val TunTunDarkColorScheme = darkColorScheme(
    primary = Color(0xFF9B7CFF),
    onPrimary = Color(0xFF160F2B),
    secondary = Color(0xFFB5A2FF),
    onSecondary = Color(0xFF211A38),
    tertiary = Color(0xFF8BE0B0),
    background = Color(0xFF0B0E17),
    onBackground = Color(0xFFF6F4FF),
    surface = Color(0xFF171D2B),
    onSurface = Color(0xFFF6F4FF),
    surfaceVariant = Color(0xFF252B3A),
    onSurfaceVariant = Color(0xFFB3B8C8),
    outline = Color(0xFF464D60)
)

@Composable
fun TunTunClockTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = TunTunDarkColorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun TungOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = { Text(label) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color(0xFFF6F4FF),
            unfocusedTextColor = Color(0xFFF6F4FF),
            focusedLabelColor = Color(0xFF9B7CFF),
            unfocusedLabelColor = Color(0xFF9CA3B5),
            focusedBorderColor = Color(0xFF9B7CFF),
            unfocusedBorderColor = Color(0xFF464D60),
            cursorColor = Color(0xFF9B7CFF)
        )
    )
}
