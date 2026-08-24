package spacewalkerconsulting.engineering.spacewalkerhub.ui.composable.screen.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import spacewalkerconsulting.engineering.spacewalkerhub.ui.theme.GradientEnd
import spacewalkerconsulting.engineering.spacewalkerhub.ui.theme.GradientStart
import spacewalkerconsulting.engineering.spacewalkerhub.ui.viewmodel.PBUHCSplashVM

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: PBUHCSplashVM = koinViewModel(),
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToOnboarding: () -> Unit,
) {
    val onboarded by viewModel.onboardedState.collectAsStateWithLifecycle()
    SplashScreenContent(modifier)
    LaunchedEffect(onboarded) {
        delay(1500)
        if (onboarded) {
            onNavigateToHomeScreen()
        } else {
            onNavigateToOnboarding()
        }
    }
}

@Composable
fun SplashScreenContent(modifier: Modifier = Modifier) {
    val alpha = remember { Animatable(0f) }
    val scale = remember { Animatable(0.8f) }
    LaunchedEffect(Unit) {
        joinAll(
            launch { alpha.animateTo(1f, tween(800)) },
            launch { scale.animateTo(1f, tween(800)) },
        )
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(GradientStart, GradientEnd))),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.Default.Engineering,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(112.dp)
                .background(Color.White.copy(alpha = 0.12f), RoundedCornerShape(28.dp))
                .padding(22.dp)
                .scale(scale.value)
                .alpha(alpha.value),
        )
        Text(
            text = "Spacewalker Engineering",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 20.dp).alpha(alpha.value),
        )
    }
}
