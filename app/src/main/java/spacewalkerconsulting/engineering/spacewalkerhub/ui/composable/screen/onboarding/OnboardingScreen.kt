package spacewalkerconsulting.engineering.spacewalkerhub.ui.composable.screen.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import spacewalkerconsulting.engineering.spacewalkerhub.ui.theme.Border
import spacewalkerconsulting.engineering.spacewalkerhub.ui.viewmodel.PBUHCOnboardingVM

private data class Page(val title: String, val description: String, val icon: ImageVector)

private val pages = listOf(
    Page("Engineering clarity", "Explore specialist consulting services designed for complex technical decisions.", Icons.Default.Engineering),
    Page("Evidence before assumptions", "Commission independent audits, energy reviews, and standards guidance from experienced consultants.", Icons.Default.Assessment),
    Page("From insight to action", "Book a focused session and leave with practical priorities for your project, asset, or system.", Icons.Default.Lightbulb),
)

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    viewModel: PBUHCOnboardingVM = koinViewModel(),
    onNavigateToHomeScreen: () -> Unit,
) {
    val saved by viewModel.onboardingSetState.collectAsState()
    LaunchedEffect(saved) {
        if (saved) {
            onNavigateToHomeScreen()
        }
    }
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { index ->
            val page = pages[index]
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier.size(152.dp).background(Border, RoundedCornerShape(28.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(page.icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(72.dp))
                }
                Text(page.title, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(top = 28.dp))
                Text(
                    page.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 12.dp),
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(20.dp)) {
            pages.indices.forEach { index ->
                Box(
                    Modifier
                        .size(if (index == pagerState.currentPage) 10.dp else 8.dp)
                        .background(if (index == pagerState.currentPage) MaterialTheme.colorScheme.secondary else Border, CircleShape)
                )
            }
        }
        Button(
            onClick = {
                if (pagerState.currentPage == pages.lastIndex) {
                    viewModel.setOnboarded()
                } else {
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                }
            },
            modifier = Modifier.fillMaxWidth().height(54.dp),
        ) {
            Text(if (pagerState.currentPage == pages.lastIndex) "Get Started" else "Next")
        }
    }
}
