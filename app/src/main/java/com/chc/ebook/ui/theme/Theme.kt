package com.chc.ebook.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.chc.ebook.utils.hex2Color

private val lightScheme = lightColorScheme(
    primary = hex2Color("#006e1c"),
    onPrimary = hex2Color("#ffffff"),
    primaryContainer = hex2Color("#b6f2af"),
    onPrimaryContainer = hex2Color("#002204"),

    secondary = hex2Color("#52634f"),
    onSecondary = hex2Color("#ffffff"),
    secondaryContainer = hex2Color("#d5e8cf"),
    onSecondaryContainer = hex2Color("#111f0f"),

    tertiary = hex2Color("#38656a"),
    onTertiary = hex2Color("#ffffff"),
    tertiaryContainer = hex2Color("#bcebf0"),
    onTertiaryContainer = hex2Color("#002023"),

    error = hex2Color("#ba1a1a"),
    onError = hex2Color("#ffffff"),
    errorContainer = hex2Color("#ffdad6"),
    onErrorContainer = hex2Color("#410002"),

    surface = hex2Color("#fcfdf6"),
    onSurface = hex2Color("#1a1c19"),
    surfaceVariant = hex2Color("#dee5d8"),
    onSurfaceVariant = hex2Color("#424940"),
    surfaceTint = hex2Color("#006e1c"),

    outline = hex2Color("#72796f"),
    outlineVariant = hex2Color("#c2c9bd"),

    inverseSurface = hex2Color("#2f312d"),
    inverseOnSurface = hex2Color("#f0f1eb"),
    inversePrimary = hex2Color("#7edb7b"),

    background = hex2Color("#fcfdf6"),
    onBackground = hex2Color("#1a1c19"),
    scrim = hex2Color("#000000"),
)

@Composable
fun EbookTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
//    // Dynamic color is available on Android 12+
//    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }

    MaterialTheme(
        colorScheme = lightScheme,
        typography = Typography,
        content = content
    )
}