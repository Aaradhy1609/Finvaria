package com.runanywhere.startup_hackathon20.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Blue Theme (Default)
private val BlueLightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE3F2FD),
    onPrimaryContainer = Color(0xFF0D47A1),
    secondary = SecondaryTeal,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFB2EBF2),
    onSecondaryContainer = Color(0xFF006064),
    tertiary = AccentOrange,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFE0B2),
    onTertiaryContainer = Color(0xFFE65100),
    background = BackgroundLight,
    onBackground = Color(0xFF1C1B1F),
    surface = SurfaceLight,
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
    error = ErrorRed,
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF93000A)
)

private val BlueDarkColorScheme = darkColorScheme(
    primary = PrimaryBlueDark,
    onPrimary = Color(0xFF003258),
    primaryContainer = Color(0xFF004A77),
    onPrimaryContainer = Color(0xFFD1E4FF),
    secondary = SecondaryTealDark,
    onSecondary = Color(0xFF003640),
    secondaryContainer = Color(0xFF004F5C),
    onSecondaryContainer = Color(0xFFB3EBFA),
    tertiary = AccentOrangeDark,
    onTertiary = Color(0xFF663C00),
    tertiaryContainer = Color(0xFF8B5000),
    onTertiaryContainer = Color(0xFFFFDCC2),
    background = BackgroundDark,
    onBackground = Color(0xFFE6E1E5),
    surface = SurfaceDark,
    onSurface = Color(0xFFE6E1E5),
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6)
)

// Purple Theme
private val PurpleLightColorScheme = lightColorScheme(
    primary = PurplePrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEDE7F6),
    secondary = PurpleSecondary,
    tertiary = PurpleAccent,
    background = BackgroundLight,
    surface = SurfaceLight
)

private val PurpleDarkColorScheme = darkColorScheme(
    primary = PurpleSecondary,
    onPrimary = Color(0xFF2E0070),
    primaryContainer = PurpleVariant,
    secondary = PurplePrimary,
    tertiary = PurpleAccent,
    background = BackgroundDark,
    surface = SurfaceDark
)

// Green Theme
private val GreenLightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFC8E6C9),
    secondary = GreenSecondary,
    tertiary = GreenAccent,
    background = BackgroundLight,
    surface = SurfaceLight
)

private val GreenDarkColorScheme = darkColorScheme(
    primary = Color(0xFF81C784),
    onPrimary = Color(0xFF003300),
    primaryContainer = GreenVariant,
    secondary = GreenSecondary,
    tertiary = GreenAccent,
    background = BackgroundDark,
    surface = SurfaceDark
)

enum class AppTheme {
    BLUE, PURPLE, GREEN, SYSTEM
}

@Composable
fun Startup_hackathon20Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    appTheme: AppTheme = AppTheme.BLUE,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> {
            when (appTheme) {
                AppTheme.PURPLE -> PurpleDarkColorScheme
                AppTheme.GREEN -> GreenDarkColorScheme
                else -> BlueDarkColorScheme
            }
        }

        else -> {
            when (appTheme) {
                AppTheme.PURPLE -> PurpleLightColorScheme
                AppTheme.GREEN -> GreenLightColorScheme
                else -> BlueLightColorScheme
            }
        }
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}